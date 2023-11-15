react

import React, { useState } from 'react';
import './App.css';
import userAvatar from './path-to-user-avatar.jpg'; // Replace with your image path
import botAvatar from './path-to-bot-avatar.jpg'; // Replace with your image path

function App() {
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');

  const sendMessage = (e) => {
    e.preventDefault();
    const newMessage = { text: input, sender: 'user' };
    setMessages([...messages, newMessage]);
    getBotResponse(input);
    setInput('');
  };

  const getBotResponse = (userInput) => {
    // Mock response - replace this with an API call or more complex logic in a real app
    const botResponse = { text: `Bot response to "${userInput}"`, sender: 'bot' };
    setMessages(messages => [...messages, botResponse]);
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>Chat with our Bot</h1>
        <div className="chat-window">
          {messages.map((message, index) => (
            <div key={index} className={message.sender === 'user' ? 'user-message' : 'bot-message'}>
              <img 
                src={message.sender === 'user' ? userAvatar : botAvatar} 
                alt={message.sender} 
                className="avatar"
              />
              <p>{message.text}</p>
            </div>
          ))}
        </div>
        <form onSubmit={sendMessage}>
          <input
            value={input}
            onChange={(e) => setInput(e.target.value)}
            type="text"
            placeholder="Type a message..."
          />
          <button type="submit">Send</button>
        </form>
      </header>
    </div>
  );
}

export default App;


/* Existing styles... */

.avatar {
  width: 30px; /* Adjust size as needed */
  height: 30px; /* Adjust size as needed */
  border-radius: 50%;
  margin-right: 10px;
}

.user-message, .bot-message {
  display: flex;
  align-items: center;
}

.user-message p, .bot-message p {
  margin: 0;
}

/* Add any additional styling as needed */
