# Console Parcels App

## Overview
The Console Parcels App is a command-line tool for managing parcels, trucks, and their loading/unloading operations. It allows users to create, find, edit, delete, and load parcels efficiently.

Additionally, the app features a **Telegram Bot** interface for easy management of parcels and trucks through chat commands. This makes interacting with the application more accessible from anywhere, directly from Telegram.

## Available Commands

### 1. Create a Parcel
Create a new parcel with a specified name, form, and symbol.
```
create -name "Квадратное колесо" -form "xxx\nx x\nxxx" -symbol "o"
```
- **`-name`**: Name of the parcel.
- **`-form`**: A multi-line string representing the parcel’s shape.
- **`-symbol`**: Character representing the parcel.

### 2. Find a Parcel
Search for a parcel by name.
```
find "Посылка Тип 5"
```

### 3. Edit a Parcel
Modify an existing parcel using its ID (name).
```
edit -id "Квадратное колесо" -name "КУБ" -form "xxx\nxxx\nxxx" -symbol "%"
```
- **`-id`**: Current name of the parcel.
- **`-name`**: New name (if changing).
- **`-form`**: New form (if updating).
- **`-symbol`**: New symbol (if modifying).

### 4. Delete a Parcel
Remove a parcel by name.
```
delete "Посылка Тип 5"
```

### 5. Load Parcels into Trucks
#### Load from Text Input
Load specified parcels into trucks using a given loading type.
```
load -parcels-text "Посылка Тип 1\nПосылка Тип 4\nКУБ" -trucks "3x3\n3x3\n6x2" -type "Одна машина - Одна посылка" -out "text"
```
- **`-parcels-text`**: List of parcel names.
- **`-trucks`**: Truck capacities.
- **`-type`**: Loading method.
- **`-out`**: Output format (`text`).

#### Load from File
Load parcels from a CSV file and save the result as a JSON file.
```
load -parcels-file "parcels.csv" -trucks "3x3\n3x3\n6x2" -type "Одна машина - Одна посылка" -out "json-file" -out-filename "trucks.json"
```
- **`-parcels-file`**: CSV file containing parcel data.
- **`-trucks`**: Truck capacities.
- **`-type`**: Loading method.
- **`-out`**: Output format (`json-file`).
- **`-out-filename`**: Name of the output file.

### 6. Unload Parcels from Trucks
#### Unload with Count
Extract parcels from a truck JSON file and store the result in a CSV file with count information.
```
unload -infile "trucks.json" -outfile "parcels-with-count.csv" --withcount
```
- **`-infile`**: Input truck JSON file.
- **`-outfile`**: Output CSV file.
- **`--withcount`**: Include count data.

#### Unload without Count
Extract parcels and save them in a CSV file without count information.
```
unload -infile "trucks.json" -outfile "parcels.csv"
```

## Notes
- Ensure parcel names are unique.
- The `form` parameter must be a valid multi-line string.
- The `symbol` must be a single character.
- Supported output formats: `text`, `json`, `csv`.
