# Tauchen — Digital Card Game

**Solo University Project**  
Developed independently as part of a software engineering course.  
*Tauchen* is a digital two-player card game implemented in **Kotlin**, focusing on turn-based game logic, UI design, and object-oriented programming.

---

## Overview

*Tauchen* is a simple but strategic card game designed for two players.  
Each player takes turns performing actions to manipulate a shared card pool.  
The goal is to form three-of-a-kind sets from the cards in the center of the table.  
Every completed set awards points, and the player with the highest score at the end wins.

---

## Screenshots

### Main Menu
<img width="1750" height="983" alt="Screenshot 2025-11-07 113612" src="https://github.com/user-attachments/assets/ea125a78-8922-462f-8438-c5af78133205" />

### Player Name Input
<img width="1752" height="981" alt="Screenshot 2025-11-07 113752" src="https://github.com/user-attachments/assets/bfae50c5-3a7c-4496-92c6-64de493fc18a" />

### Gameplay Examples
<img width="1750" height="979" alt="Screenshot 2025-11-07 114007" src="https://github.com/user-attachments/assets/5bc9d4f8-732c-4d76-b3b0-618dcc80d590" />
<img width="1754" height="982" alt="Screenshot 2025-11-07 114405" src="https://github.com/user-attachments/assets/128eeb86-bb90-44aa-868a-d8fe028c1d85" />

### Game Over Screen
<img width="1750" height="980" alt="Screenshot 2025-11-07 114447" src="https://github.com/user-attachments/assets/3cf4f20d-457d-4794-a04f-c1716cb56a67" />

---

## Gameplay Mechanics

Each turn, a player can perform one of several actions:

- **Play** – Place a card from your hand into the shared center area.  
- **Draw** – Draw a new card from the deck.  
- **Swap** – Swap a card in your hand with a card in the center.  
- **Discard** – Remove a card from your hand.  

When three cards of the same type appear in the center, they are automatically removed, and the active player earns points.  
The game continues until no further moves are possible or the deck is empty.

---

## Features

- Fully turn-based gameplay system  
- Dynamic card and scoring logic  
- Randomized deck and card pool generation  
- Automatic detection and scoring of matching sets  
- Simple, clear user interface implemented in Kotlin  

---

## Development Details

- **Language:** Kotlin  
- **Build Tool:** Gradle  
- **IDE:** IntelliJ IDEA  
- **Version Control:** Git & GitHub  

The project was originally developed in a private GitLab repository as part of the course workflow and has been migrated to GitHub for portfolio presentation.

This was a solo project covering all aspects of game logic, user interface, architecture, and code quality.

---

## How to Play

1. Start the game and enter both player names.  
2. A shared deck and central card pool are initialized.  
3. Players alternate turns, choosing between Play, Draw, Swap, or Discard.  
4. When a three-of-a-kind appears in the center, it is automatically removed and points are assigned.  
5. The game ends when no further actions are possible or the deck is empty.

---

## Educational Purpose

This project demonstrates:
- Kotlin programming and Gradle build configuration  
- Turn-based state management  
- Object-oriented software design  
- Clean architecture and modular coding practices
- Use of GitLab for version control during development

---

© 2025 Jason Ball.  
This project was created for educational and portfolio purposes.  
Commercial use or redistribution of included code and assets is not permitted.
