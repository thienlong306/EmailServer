
# EmailServer

Building a Mail Server System with Java Socket(TCP/IP)



## Features

- Client (with GUI):
    - The functions of sending and receiving mail are similar to a simple mail client:
        - Send (including CC and BCC) to 1 or more email addresses: email content can be simply formatted (font size, font color, bold/italic, ...) and can embed images, attachments .
        - Receive
        - Reply; reply all
        - Mark spam emails
        - Schedule send
        - Delete
        - OpenFile
    - Simulate how to categorize emails into groups: Inbox (for incoming and unread emails); Read (email has been read); Sent (for sent email); Spam (for emails marked as spam); Bin (for deleted emails; Drafts Offline (for draft emails you want to save locally).
    - User information, data, change password
- Server (no GUI):
    - The server logs the client's login/logout process.
    - The server can set and change the user's mailbox size. Default is 100 MB/user.
    - The server can block or send email notifications to 1 user or all users.
- Data encryption (Hybrid Encryption)
    - AES . symmetric key encryption
    - RSA . asymmetric encryption

## Installation
```bash
  Open folder EmailServer on IntelliJ IDEA
  Add lib in folder lib
  Run
```

    
## Screenshots

![App Screenshot](/Doc/images.png "Ảnh Demo")
