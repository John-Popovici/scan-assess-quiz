# scan-assess-quiz

Simple CLI tool that:
- automatically detects the local network range
- runs an `nmap` ping scan (`-sn`) on a target range
- parses discovered device IPs and hostnames
- asks you to guess how many devices are up
- reveals the result and lists discovered devices

## Prerequisites

- Python 3.12+
- `nmap` installed and available on your `PATH`

## Run

```bash
python3 main.py
```

You can enter a custom target network/range (for example `192.168.1.0/24`) or press Enter to use the default.
