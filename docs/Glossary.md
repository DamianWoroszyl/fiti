
# Glossary

**Macro** - a grouping term for products macroelement parts: kcal, carbs, protein and fat.

**Product** - fundamental element of a diet - can be eaten standalone or be a part of a meal.

**Consumed Product** - an eaten product with grammage

**Dish** - a reusable list of consumed products

**Dish Product** - an information about product within a given dish

**Dish Variation** - a Dish with adjusted grammage for each product. It's not a separate dish from user's perspective, but rather a variation. A dish configuration is it's base variation

**Consumed Dish** - an eaten Dish variation, information on how much of that Dish's variation was eaten. Contains a list of Consumed Products

**Meal** - an occasion when food is eaten. A day is split into several configured meals. During a meal products and dishes can be consumed

**Consumed Meal** - a meal consumed on particular day, with particular set of Consumed Products and Consumed Dishes

### Consumption
Relation between Consumed Products and Consumed Meal or Consumed Dish should be set in data
source, probably SQL DB as many to many relation