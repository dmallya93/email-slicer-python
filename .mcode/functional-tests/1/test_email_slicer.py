"""Functional tests for the email-slicer CLI tool.

Tests verify the CLI reads an email from stdin and outputs the username and domain,
or an error message for invalid input. The binary path is configured via the
EMAIL_SLICER_BIN environment variable.
"""

import os
import subprocess
import pytest

# The binary path is set by the test runner environment.
# For Go target: the compiled binary path
# For Python origin: "python emailSlicer.py" invoked via shell
BIN_PATH = os.environ.get("EMAIL_SLICER_BIN", "./email-slicer")
WORKING_DIR = os.environ.get("EMAIL_SLICER_CWD", ".")


def run_cli(input_text):
    """Helper to invoke the email-slicer CLI with piped stdin."""
    # Determine if we need shell mode (for python commands)
    use_shell = " " in BIN_PATH
    if use_shell:
        cmd = BIN_PATH
    else:
        cmd = [BIN_PATH]

    result = subprocess.run(
        cmd,
        cwd=WORKING_DIR,
        capture_output=True,
        text=True,
        timeout=30,
        input=input_text,
        shell=use_shell,
    )
    return result


class TestValidEmail:
    """HAPPY_PATH: Valid email addresses that contain @."""

    def test_standard_email(self):
        """Standard email address with username and domain."""
        result = run_cli("testuser@example.com\n")
        assert result.returncode == 0
        assert "Please enter your Email Id:" in result.stdout
        assert "Your username is:  testuser" in result.stdout
        assert "Your domain is:  example.com" in result.stdout

    def test_email_with_subdomain(self):
        """Email with a subdomain in the domain part."""
        result = run_cli("user@mail.example.com\n")
        assert result.returncode == 0
        assert "Your username is:  user" in result.stdout
        assert "Your domain is:  mail.example.com" in result.stdout


class TestMultipleAtSigns:
    """BOUNDARY: Email with multiple @ signs - splits on first @."""

    def test_double_at(self):
        """Input with @@ should split on the first @."""
        result = run_cli("user@@domain.com\n")
        assert result.returncode == 0
        assert "Your username is:  user" in result.stdout
        assert "Your domain is:  @domain.com" in result.stdout

    def test_at_in_domain(self):
        """Input with @ in both username and domain parts."""
        result = run_cli("user@middle@domain.com\n")
        assert result.returncode == 0
        assert "Your username is:  user" in result.stdout
        assert "Your domain is:  middle@domain.com" in result.stdout


class TestBareAt:
    """BOUNDARY: Just the @ character with no username or domain."""

    def test_bare_at_sign(self):
        """A single @ should be valid (matches Python behavior)."""
        result = run_cli("@\n")
        assert result.returncode == 0
        assert "Your username is:  " in result.stdout
        assert "Your domain is:  " in result.stdout


class TestInvalidEmail:
    """INVALID_ARGS: Input without @ should produce an error message."""

    def test_no_at_sign(self):
        """Plain text without @ is invalid."""
        result = run_cli("invalidemail\n")
        assert result.returncode == 0
        assert "Please enter a valid Email Id." in result.stdout

    def test_empty_input(self):
        """Empty input (just newline) is invalid."""
        result = run_cli("\n")
        assert result.returncode == 0
        assert "Please enter a valid Email Id." in result.stdout


class TestWhitespace:
    """BOUNDARY: Input with leading/trailing whitespace should be trimmed."""

    def test_whitespace_padded_email(self):
        """Whitespace around a valid email should be stripped."""
        result = run_cli("  user@test.com  \n")
        assert result.returncode == 0
        assert "Your username is:  user" in result.stdout
        assert "Your domain is:  test.com" in result.stdout
