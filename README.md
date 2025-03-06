# MagicApp

**MagicApp** is an Android application designed to help **Magic: The Gathering** players manage their card collection, build decks, and analyse statistics.

## 🚀 Features
- 📜 **Card Lookup**: Browse the MTG card catalogue using the **Scryfall** API.
- 📦 **Collection Management**: Store your owned cards and their quantities locally.
- 🃏 **Deck Building**: Create decks while adhering to the rules of various game formats.
- 📊 **Deck Statistics**: Analyse mana curves, colour distribution, and card count.
- 💾 **Local Storage**: Uses **Room** to store data and prevent unnecessary downloads.
- 🖼 **Optimised Image Loading**: Downloads and stores card images locally, ensuring efficient loading via **Coil**.

## 📦 Technologies Used
- **Language:** Kotlin
- **Database:** Room
- **API:** Scryfall
- **Image Loading:** Coil
- **User Interface:** XML

## 📥 Installation and Setup
1. Clone the repository:
```sh
   git clone https://github.com/diegoherrub/magic-app.git
   cd magicapp
```
Open the project in Android Studio.
Ensure you have an emulator or a physical device configured.
Run the app on your device or emulator.

##📡 Scryfall API Integration
The application retrieves card data from Scryfall. An initial query is performed to fetch the cards and store them in the local database.

## 🛠 Project Status
- [x] API connection and Room storage.
- [ ] Implementation of collection management.
- [ ] Deck creation with format validation.
- [ ] Deck statistics visualisation.

📖 Full Documentation → [View on GitHub Pages](https://diegoherrub.github.io/magic-app/)

📜 Licence
This project is licensed under the MIT Licence. See the [LICENSE](LICENSE) file for more details.
