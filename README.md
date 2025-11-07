# Tauchen — Digital Card Game (Kotlin)

**Solo University Project**  
Developed independently as part of a software engineering course.  
*Tauchen* is a digital two-player card game implemented in **Kotlin**, focusing on turn-based game logic, UI design, and object-oriented programming.

---

## Overview

*Tauchen* is a simple but strategic card game designed for two players.  
Each player takes turns performing actions to manipulate a shared card pool.  
The goal is to form **three-of-a-kind sets** from the cards in the center of the table.  
Every completed set awards points, and the player with the highest score at the end wins.

---

## Gameplay Mechanics

Each turn, a player can perform one of several actions:

- **Play** – Place a card from your hand into the shared center area.  
- **Draw** – Draw a new card from the deck.  
- **Swap** – Exchange a card between your hand and the center.  
- **Discard** – Remove a card from play.  

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

This project was developed individually, covering all aspects of game logic, user interface, and project structure.

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

---

© 2025 Jason Ball.  
This project was created for educational and portfolio purposes.  
Commercial use or redistribution of included code and assets is not permitted.
