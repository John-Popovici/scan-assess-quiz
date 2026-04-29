# scan-assess-quiz

Broken down into sections, depending on code base:

## Python
Simple CLI tool that:
- automatically detects the local network range
- runs an `nmap` ping scan (`-sn`) on a target range
- parses discovered device IPs and hostnames
- asks you to guess how many devices are up
- reveals the result and lists discovered devices

### Prerequisites

- Python 3.12+
- `nmap` installed and available on your `PATH`

### Run

```bash
python3 main.py
```

You can enter a custom target network/range (for example `192.168.1.0/24`) or press Enter to use the default.

## Java
Works using Springboot and Vaadin, creates a simple website, that uses localhost:8080 for asking the user some questions, populated by the Python call to get the information installed on the device.

Questions are stored in the questions folder as JSON entries 
### Prerequisites
Java 25
Maven tool, for building project and adding dependencies

### Run

```maven
mvn clean install
```

on QuizSelectorGame
And then run QuizSelectorGameApplication
Will be compiled into a WAR later

