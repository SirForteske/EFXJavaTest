# EFXJavaTest
When executing the app, you can pass as many paths to CSV files as you want as arguments.

# ASSUMPTIONS
For this test I have made the followign assumptions:
1. A price should only updated if the new incoming price matches its same id, otherwise this new price is stored separately.
2. When a new message arrives, the data it contains is always the most recent.
