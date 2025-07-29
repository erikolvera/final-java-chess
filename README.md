# ♟️ Java Chess Game  
### CS 3354.751 – Summer 2025  
**Developed by Erik Olvera, Sam Chutter, Marco Mosqueda**

---

## 📌 Overview  
This project is a two player Java Chess Game and features:

- Turn-based gameplay
- Highlighted piece selection with green borders
- Customizable board colors via a user-friendly color picker

---

##  Screenshots  

| Piece Selection | Color Customization |
|-----------------|---------------------|
| <img width="175" height="175" alt="Piece Selection Screenshot" src="https://github.com/user-attachments/assets/2e06a73f-06fd-4d25-a694-d1d3d4201415" /> | <img width="175" height="175" alt="Color Picker Screenshot" src="https://github.com/user-attachments/assets/c2f3e80f-42dc-4585-be72-73a73c8d1acd" /> |
| Selected pieces are outlined in green | Users can choose custom board colors |

---

##  Features  
-  **Interactive Board**: Click to select and move pieces with visual feedback.  
-  **Custom Color Themes**: Pick your own board color from a popup panel.  
-  **Endgame Detection**: Capturing a king ends the game with a winner announcement.

---

## ⚙️ How to Compile and Run

Compile all files at the root with - 
```javac -d out Java-Chess-Game/board/Board.java Java-Chess-Game/pieces/*.java Java-Chess-Game/player/Player.java Java-Chess-Game/position/Position.java gui/*.java ```
- That will put the output file in a folder called out.
run on terminal at the root  - java -cp out gui.boardGUI
