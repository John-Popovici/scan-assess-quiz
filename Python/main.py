from __future__ import annotations

import re
import socket
import subprocess
import sys


DEFAULT_TARGET = "192.168.1.0/24"


class Device:
    ip: str
    name: str | None

    def __init__(self, ip: str, name: str | None = None) -> None:
        self.ip = ip
        self.name = name


def get_local_ipv4() -> str | None:
    """Attempts to determine the local IPv4 address."""
    try:
        with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as sock:
            sock.connect(("8.8.8.8", 80))
            candidate = sock.getsockname()[0]
            if candidate and candidate.count(".") == 3 and not candidate.startswith("127."):
                return candidate
    except OSError:
        pass

    try:
        candidate = socket.gethostbyname(socket.gethostname())
        if candidate and candidate.count(".") == 3 and not candidate.startswith("127."):
            return candidate
    except OSError:
        pass

    return None


def default_target_from_local_ip() -> str:
    """Infers a default target network range."""
    local_ip = get_local_ipv4()
    if not local_ip:
        return DEFAULT_TARGET

    # Assume a /24 subnet and replace the last octet with 0/24
    octets = local_ip.split(".")
    if len(octets) != 4:
        return DEFAULT_TARGET
    return f"{octets[0]}.{octets[1]}.{octets[2]}.0/24"


def run_nmap_ping_scan(target: str) -> str:
    """Runs an nmap ping scan and returns the grepable output."""
    command = ["nmap", "-sn", "-oG", "-", target]

    try:
        result = subprocess.run(
            command,
            capture_output=True,
            text=True,
            check=True,
        )
    except FileNotFoundError as exc:
        raise RuntimeError(
            "nmap is not installed or not on PATH. Please install nmap first."
        ) from exc
    except subprocess.CalledProcessError as exc:
        stderr = exc.stderr.strip() if exc.stderr else "No stderr output."
        raise RuntimeError(f"nmap scan failed: {stderr}") from exc

    return result.stdout


def parse_devices_from_grepable_output(output: str) -> list[Device]:
    """Parses nmap grepable output to extract discovered devices."""
    devices: dict[str, Device] = {}
    # Extract address and hostname for only Status: Up
    line_pattern = re.compile(r"^Host:\s+(\d+\.\d+\.\d+\.\d+)\s+\((.*?)\)\s+Status:\s+Up")

    for line in output.splitlines():
        line = line.strip()
        match = line_pattern.match(line)
        if not match:
            continue

        ip = match.group(1).strip()
        raw_name = match.group(2).strip()
        name = raw_name if raw_name else None
        devices[ip] = Device(ip=ip, name=name)

    return sorted(devices.values(), key=lambda device: tuple(int(part) for part in device.ip.split(".")))


def main() -> None:
    """Run the scan and quiz."""
    print("Scan Assess Quiz")
    print("Runs an nmap ping scan, then quizzes your device count guess.\n")

    # Scan the network
    inferred_target = default_target_from_local_ip()
    use_assumed = input(f"Use assumed network/range [{inferred_target}] (y/n): ").strip().lower() == "y"
    if not use_assumed:
        target = input(f"Enter target network/range: ").strip() or inferred_target
    else:
        target = inferred_target
    print(f"\nScanning target: {target}")

    try:
        output = run_nmap_ping_scan(target)
    except RuntimeError as error:
        print(f"Error: {error}")
        sys.exit(1)

    devices: list[Device] = parse_devices_from_grepable_output(output)

    # Quiz the user
    guess = int(input("How many devices do you think are up on this network? ").strip())
    actual = len(devices)
    print("\n=== Quiz Result ===")
    print(f"Your guess: {guess}\nActual devices up: {actual}")
    if guess == actual:
        print("Nice! Exact match.")
    else:
        diff = abs(guess - actual)
        direction = "higher" if guess < actual else "lower"
        print(f"Off by {diff}, the actual answer is {direction} than your guess.")

    # Show discovered devices
    print("\n=== Discovered Devices ===")
    if not devices:
        print("No devices found.")

    for index, device in enumerate(devices, start=1):
        name = device.name if device.name else "(unknown name)"
        print(f"{index:>2}. {device.ip} - {name}")


if __name__ == "__main__":
    main()
