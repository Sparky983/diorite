# Table of Contents

1. [About](#about)
2. [Stability Note](#stability-note)

## About

A low-level api for interacting with the Minecraft protocol

## Stability Note

Since the Mojang does not take care to ensure any compatibility between different versions of the
protocol, this module's api cannot feasibly be stable either. For stable api, please use the client
and server modules.

Elements annotated with `@ApiStatus.Experiemental` are extremely unstable due to reasons such as not
being properly implemented yet. No care will be taken to ensure any compatability with these 
elements.
