# Manopoly: A Web-Based Monopoly Game

A full-stack implementation of the classic Monopoly board game with multiplayer functionality.

## Overview

Manopoly is a web-based multiplayer implementation of the Monopoly board game. It features a React frontend and Java Spring backend, allowing multiple players to join games, roll dice, purchase properties, and engage in the classic Monopoly experience.

## Features

- **User Authentication**: Simple username-based login system with cookie persistence
- **Game Creation**: Create new game boards or join existing ones with a board code
- **Real-Time Gameplay**: Synchronous multiplayer experience with board state updates
- **Dice Rolling**: Animated dice rolling with proper game movement
- **Property Management**: Purchase properties when landing on unowned spaces
- **Visual Board**: Complete Monopoly board layout with color-coded properties
- **Player Tokens**: Customizable player colors
- **Turn Management**: Proper turn rotation between players

## Tech Stack

### Frontend
- **React.js**: Component-based UI architecture
- **CSS**: Custom styling with responsive design
- **Fetch API**: RESTful communication with the backend

### Backend
- **Java**: Core programming language
- **Spring Framework**: Web application framework
- **JPA/Hibernate**: ORM for database interactions
- **Servlet Async Context**: For long-polling subscription mechanism

## Project Structure

```
├── frontend/
│   ├── App.jsx                   # Main application component
│   ├── index.jsx                 # Entry point
│   ├── components/
│   │   ├── Login.jsx             # Login component
│   │   ├── HomePage.jsx          # Home page with board creation/joining
│   │   └── BoardComponents/
│   │       ├── Board.jsx         # Game board component
│   │       ├── BoardSquare.jsx   # Individual board square
│   │       ├── ColourSelection.jsx # Player color picker
│   │       ├── CardAction.jsx    # Property purchase action
│   │       └── DiceRoll.jsx      # Dice rolling component
│   └── css/
│       ├── Board.css             # Board styles
│       ├── BoardSquare.css       # Square styles
│       ├── ColourSelection.css   # Color selection styles
│       ├── DiceRoll.css          # Dice animation styles
│       ├── HomePage.css          # Home page styles
│       └── Login.css             # Login page styles
│
├── backend/
│   ├── models/
│   │   ├── Board.java            # Board entity
│   │   ├── BoardSquare.java      # Abstract board square
│   │   ├── Property.java         # Property board square
│   │   ├── City.java             # City property
│   │   ├── Train.java            # Train station property
│   │   ├── Utility.java          # Utility property
│   │   ├── NotProperty.java      # Non-property squares
│   │   ├── Player.java           # Player entity
│   │   ├── Position.java         # Player position
│   │   └── Colour.java           # Player color
│   │
│   ├── dtos/
│   │   ├── BoardDTO.java
│   │   ├── BoardSquareDTO.java
│   │   ├── PlayerDTO.java
│   │   ├── PropertyDTO.java
│   │   ├── TileActionDTO.java
│   │   └── CardPurchaseActionDTO.java
│   │
│   ├── services/
│   │   ├── BoardResource.java
│   │   ├── PlayerResource.java
│   │   ├── CardActionResource.java
│   │   └── BoardSubscriptionManager.java
│   │
│   └── utils/
│       ├── Mapper.java
│       ├── PropertyUtils.java
│       ├── TileActions.java
│       ├── CityName.java
│       ├── PropertyType.java
│       ├── TrainName.java
│       ├── UtilityName.java
│       └── PlayerColour.java
```

## Setup & Installation

### Prerequisites
- Node.js and npm
- Java Development Kit (JDK) 11 or higher
- Maven

### Frontend Setup
1. Navigate to the frontend directory
2. Install dependencies:
   ```
   npm install
   ```
3. Start the development server:
   ```
   npm start
   ```
4. Access the application at `http://localhost:3000`

### Backend Setup
1. Navigate to the backend directory
2. Build the application:
   ```
   mvn clean install
   ```
3. Run the application:
   ```
   mvn spring-boot:run
   ```
4. The server will start at `http://localhost:8080`

## Game Rules

The game follows standard Monopoly rules:

1. Players take turns rolling dice and moving around the board
2. When landing on an unowned property, players can purchase it
3. When landing on a property owned by another player, rent must be paid
4. Players collect money when passing GO

## Current Status & Future Enhancements

The game currently implements core Monopoly functionality with the following features in development:

- [ ] Chance and Community Chest cards
- [ ] Property trading between players
- [ ] Building houses and hotels
- [ ] Jail mechanics
- [ ] Bankruptcy handling
- [ ] Game statistics and history
- [ ] Enhanced animations and UI improvements