compile:
	gcc server.c -o tcp_server_bin
	gcc client.c -o tcp_client_bin

clean:
	rm *_bin