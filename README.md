# Waystone Emeralds

Waystone Emeralds adds emerald-based payment helpers for Waystones warp requirements.

It does not add its own config file. Pack makers keep using Waystones' existing `warpRequirements` list and replace XP cost rules with the emerald cost functions registered by this mod.

## Functions

Use these functions in `config/waystones-common.toml`:

```toml
[teleports]
enableCosts = true
warpRequirements = [
  "[is_not_interdimensional] add_emerald_cost(1)",
  "[is_interdimensional] add_emerald_cost(3)",
  "[source_is_scroll] multiply_emerald_cost(0)",
  "min_emerald_cost(0)",
  "max_emerald_cost(3)"
]
```

This example mirrors the common Waystones XP setup, but charges emerald units instead:

- Same-dimension warp: 1 emerald.
- Interdimensional warp: 3 emeralds.
- Scrolls: no extra emerald cost.
- Emerald blocks count as 9 emeralds and return change as emeralds.

## Lightman's Currency

If Lightman's Currency is installed, the requirement can also draw from the player's Lightman's money handler.

The mod checks Lightman's configured money data before using a payment item:

1. `minecraft:emerald`
2. `lightmanscurrency:coin_emerald`

This keeps packs that register vanilla emeralds as Lightman's money working, while still supporting Lightman's original emerald coin when vanilla emeralds are not configured as money.

## Requirements

- Minecraft 1.21.1
- NeoForge
- Waystones
- Balm
- Lightman's Currency is optional
