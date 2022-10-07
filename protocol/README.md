# Table of Contents

1. [About](#about)
2. [Stability Note](#stability-note)

## About

A low-level api for interacting with the Minecraft protocol

## Stability Note

Since the Mojang does not take care to ensure any compatibility between different versions of the
protocol, the packet api cannot feasibly be stable either. For stable api, please use the client
and server modules.

Elements inside the packet package that are annotated with `@ApiStatus.Experiemental` are considered
extremely unstable due to reasons such as not being properly implemented yet. No care will be taken 
to ensure any compatability with these elements.
