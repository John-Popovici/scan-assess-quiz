# Enumeros

Enumeros is a small Rust-based local assessment helper for collecting basic host, browser, and local network information.

It is intended to support environment-aware cybersecurity questionnaires and risk-assessment workflows.

## Features

- Detects host OS, OS version, and kernel version
- Detects installed browser versions where available
- Compares selected software versions against latest-version sources
- Performs low-impact local subnet TCP connect checks against a small set of common ports
- Emits structured JSON output for downstream tools

## Prerequisites

- Rust toolchain
- No `nmap` dependency

Enumeros does not require `nmap`. Network discovery uses TCP connect checks from the Rust program itself.

## Build

```bash
cargo build --release
