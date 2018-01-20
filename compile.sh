#!/bin/bash
gcc main.c -o tcp_server_bin
gcc tcp_client/main.c -o ./tcp_client_bin