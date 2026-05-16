# Waystone Emeralds

Waystone Emeralds adds emerald-based payment helpers for Waystones warp requirements.

It adds its own `config/waystone_emeralds-common.toml` file. When enabled, it applies emerald-cost rules to Waystones at runtime, so pack makers do not need to edit `config/waystones-common.toml` for this feature.

## Config

Default `config/waystone_emeralds-common.toml`:

```toml
[teleports]
enableEmeraldCosts = true
sameDimensionCost = 1
interdimensionalCost = 3
scrollCostMultiplier = 0.0
minCost = 0
maxCost = 3
```

The default config mirrors a simple fixed Waystones XP setup, but charges emerald units instead:

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
