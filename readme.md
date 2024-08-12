## IRC Client

[![randown](https://aqclf.xyz/randown/randown.svg)](https://aqclf.xyz/randown?username=aquaticcalf&reponame=irc-client)

This project is a simple IRC (Internet Relay Chat) client built using Java Swing for the GUI and Java networking libraries for the IRC communication.  

### Features
- Connect to an IRC server 
- Join a specific channel 
- Send and receive messages 
- Simple GUI with fields for server, port, nickname, username, and channel

### Requirements
Java Development Kit (JDK) 8 or higher

### How to Run
1. Clone the repository:  
```
git clone https://github.com/aquaticcalf/irc-client
cd irc-client
```
2. Compile the Java file:  
```
javac IRCClient.java
```
3. Run the application:  
```
java IRCClient
```

### Usage
1. Start the application.
2. Enter the following details:
   - Server: The IRC server address (e.g., irc.example.com)
   - Port: The port number (e.g., 6667)
   - Nickname: Your desired nickname 
   - Username: Your username 
   - Channel: The channel you want to join (e.g., #your_channel)
3. Click the "Connect" button to connect to the server.
4. Use the input field at the bottom to type messages and click "Send" or press Enter to send them.

### Code Overview
- GUI Setup: The main method sets up the GUI using Java Swing components.
- Connection Handling: The `connectToServer` method handles connecting to the IRC server and starting a new thread to listen for incoming messages.
- Message Sending: The `sendMessage` method sends messages to the IRC server.
### License
This project is licensed under the MIT License. See the [license](license.md) file for more details.