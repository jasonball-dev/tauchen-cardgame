# Tauchen — Digital Card Game (Kotlin)

**University Project**  
Developed as part of a software engineering course with a focus on Java/Kotlin-based application design, state management, and turn-based logic.  
*Tauchen* is a digital two-player card game that combines memory, risk, and strategic decision-making.

---

## Overview

In *Tauchen*, two players take turns performing actions to manage and manipulate a shared card pool.  
The goal is to create **three-of-a-kind sets** from the cards in the center of the table.  
Each completed set awards points, and the player with the highest score at the end wins.

---

## Gameplay Mechanics

Each turn, a player can perform one of several actions:

- **Play** – Play a card from your hand into the center.  
- **Draw** – Draw a new card from the deck.  
- **Swap** – Exchange a card between your hand and the center.  
- **Discard** – Remove a card from play.  

Whenever a **three-of-a-kind** appears in the center, it is automatically removed, and the player earns points.

The game continues until no further valid moves are possible or the deck is empty.

---

## Features

- Turn-based gameplay for two players  
- Dynamic card pool and scoring system  
- Randomized deck generation  
- Simple, intuitive interface  
- Automatic recognition of three-of-a-kind sets  

---

## Development Details

- **Language:** Kotlin  
- **Build Tool:** Gradle  
- **IDE:** IntelliJ IDEA  
- **Version Control:** Git & GitHub  

Focus areas during development included turn-based game logic, state persistence, and modular UI design.

---

## How to Play

1. Start the game and enter player names  
2. The game initializes a shared deck and center pool  
3. Players alternate turns and select one of the available actions  
4. When a three-of-a-kind is created, it is removed automatically and points are assigned  
5. The game ends when no more moves are possible  

---

## Educational Purpose

This project was designed to demonstrate:
- Kotlin fundamentals  
- Object-oriented architecture in a game context  
- Turn-based state management  
- Collaboration and version control practices  

---

## Credits

Developed as part of a university course on software engineering.  
Game rules and mechanics are original concepts created for this project.

---

© 2025 Jason Ball.  
This project was created for educational and portfolio purposes.  
Commercial use or redistribution of included code and assets is not permitted.

---
