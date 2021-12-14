# Java Smart Contracts

This repository demonstrates a tictactoe smart gameplay contract written in Java that run on the NEO blockchain.

# Setup

The easiest way to interact with this project is in your browser. Visit the neo playground: [playground](https://neo-playground.dev/). Open the java web-ide. Load the tictactoe smart-contract from this repo, `src/main/java/io/neow3j/examples/contractdevelopment/contracts/TicTacToe.java`, into Neo's contract directory `src/main/java/io/neow3j/examples/contractdevelopment/contracts`. Add the tictactoe deploy file `src/main/java/io/neow3j/examples/contractdevelopment/DeployTicTacToe.java`. Change `className`'s value in `build.gradle` to `"io.neow3j.examples.contractdevelopment.contracts.TicTacToe" `

# Instructions

The following instructions assume that the Web IDE was started successfully (see [Setup](#setup)).

## Opening a terminal

To open a terminal, use the Terminal drop-down menu in the IDE user interface.

# Steps

## 1 - Start a local blockchain

Run this in a terminal:

```shell
neoxp run
```

## 2 - View and create accounts/wallets

In a new terminal window, list existing wallets:

```shell
neoxp wallet list
```

> ...
> Alice

    NM7Aky765FG8NhhwtxjXRx7jEL1cnw7PBP (Default)

> ...
> Bob
> NV1Q1dTdvzPbThPbSFz7zudTmsmgnCwX6c (Default)

Create your own wallet by running something like this (replace "Colin" w/your name):

```shell
neoxp wallet create Colin
```

> Colin

    NcegmgmiTL8wAh18S2KYvWxGJP5ygAwHcG

## 3 - Compile the contract

In the root/workspace directory, run this in your terminal:

```shell
./gradlew neow3jCompile
```

This will compile the smart contract class (specified in `build.gradle`, and output a NEF file, contract manifest, and debugging information file to the output directory, by default at `build/neow3j`

## 4 - Deploy the contract

The easiest way to deploy the contract is by navigating to the deployment java file and clicking the VsCode `run` button above the main method.

For the TicTacToe contract, this deployment file is at: `src/main/java/io/neow3j/examples/contractdevelopment/DeployTicTacToe.java`

If successful, you'll see something like this in your terminal:

> The contract was deployed in transaction f062397...
> Script hash of the deployed contract: f8d63b2f0d...
> Contract Address: Ngzq4QyGhvnRmZHLuhkWjuvZx8NHDE2yZn

To list your deployed contracts, run:

```shell
neoxp contract list
```

> ...
> TicTacToe (0xf8d63b2f0db3b3f03bbb1e314425b17a02f045e7)

> > **Troubleshooting:** _In case of \"Insufficient GAS\", then you'll need to to transfer GAS to `Alice's` wallet (the default deployer)._ Run:
> >
> > ```shell
> > neoxp transfer 100000 GAS genesis Alice
> > ```
> >
> > In case of \"Failed to connect\" or \"Connection refused\", start the Neo node by running:
> >
> > ```shell
> > neoxp run
> > ```

## 5 - Use the contracts, play tic-tac-toe!

For this project, I made a tic-tac-toe smart contract.
The tic-tac-toe board is represented as an array that holds 9 ints`[0,0,0,0,0,0,0,0,0]`:
The indexes represent the locations:

```
      0, 1, 2
      3, 4, 5
      6, 7, 8
```

Player 1's marker is "1".
Player 2's marker is "2".

A final game where player 1 wins might be stored as `[2,1,2,1,1,2,2,1,2]` which looks like:

```
      2, 1, 2
      1, 1, 2
      2, 1, 2
```

Here's how to interact with the contracts via the terminal.

```shell
#start a game between Alice and Bob
neoxp contract run TicTacToe startGame @Alice @Bob -a Alice

#Make moves
neoxp contract run TicTacToe takeTurn 0 @Bob -a Bob
neoxp contract run TicTacToe takeTurn 1 @Alice -a Alice
neoxp contract run TicTacToe takeTurn 2 @Bob -a Bob
neoxp contract run TicTacToe takeTurn 3 @Alice -a Alice
neoxp contract run TicTacToe takeTurn 4 @Alice -a Alice
neoxp contract run TicTacToe takeTurn 5 @Bob -a Bob
neoxp contract run TicTacToe takeTurn 6 @Bob -a Bob

#Winning move (3 in a the middle column)
neoxp contract run TicTacToe takeTurn 7 @Alice -a Alice

#See the gameboard
neoxp contract run TicTacToe getBoard -r
```

> ```
>     2, 1, 2
>     1, 1, 2
>     2, 1, 0
> ```

```shell
#Check if game is over
neoxp contract run TicTacToe isGameOver -r

#Get Final getWinner
neoxp contract run TicTacToe getWinner -r
```

# Terminal command reference

Most commands use [Neo-Express](https://github.com/neo-project/neo-express/blob/master/docs/command-reference.md). Run this in the terminal for options:

```shell
neoxp --help
```

> Commands:
>
> > batch - Execute a series of offline Neo-Express operations
> > checkpoint - Manage neo-express checkpoints
> > contract - Manage smart contracts
> > create - Create new neo-express instance
> > export - Export neo-express protocol, config and wallet files
> > oracle - Manage oracle nodes and requests
> > policy - Manage blockchain policy
> > reset - Reset neo-express instance node
> > run - Run Neo-Express instance node
> > show - Show information
> > stop - Stop Neo-Express instance node
> > transfer - Transfer asset between accounts
> > wallet - Manage neo-express wallets

## Requirements

At least Java 8 is required.

Additionally, many examples will require a Neo network. I recommend using [_Neo Express_](https://github.com/neo-project/neo-express) for that purpose. It is integrated into the [_Neo Blockchain Toolkit_](https://marketplace.visualstudio.com/items?itemName=ngd-seattle.neo-blockchain-toolkit) that is available on the VSCode Extension Marketplace.

All examples are using [_Gradle_](https://gradle.org/) as the build tool and provide a Gradle wrapper. So, you don't necessarily need Gradle installed on your machine.

## Instructions

I recommend using [_Virtual Studio Code_](https://code.visualstudio.com/) (VSCode) for trying these examples. You can then make use of the [_Neo Blockchain Toolkit_](https://marketplace.visualstudio.com/items?itemName=ngd-seattle.neo-blockchain-toolkit) which gives you the best developer experience with Neo. Checkout their [Quickstart Guide](https://github.com/neo-project/neo-blockchain-toolkit/blob/master/quickstart.md) for how to set it up.

With VSCode you should install the [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) and the [Gradle Extension Pack](https://marketplace.visualstudio.com/items?itemName=richardwillis.vscode-gradle-extension-pack) for Java and Gradle support.

These examples are accompanied by code tours. To make use of them install the VSCode [_Code Tours_](https://marketplace.visualstudio.com/items?itemName=vsls-contrib.codetour) extension.

Once you have VSCode setup, clone the whole examples repository and open this directory with VSCode. Now you can build the project with `./gradlew build`. Some examples don't depend on a running Neo network, those can be executed immediately, e.g., via the `Run` buttons above the `main` methods. All other examples will require a running Neo network. Setting this up with the help of the _Neo Blockchain Toolkit_ is explained in the code tours.

## Contributing

[Contributing Guide](./CONTRIBUTING.md)

[Code of Conduct](./CONTRIBUTING.md#conduct)

## License

[MIT](./LICENSE)

## Important

See [LICENSE](LICENSE) for copyright and license terms.

All repositories and other materials are provided subject to the terms of this [IMPORTANT](important.md) notice and you must familiarize yourself with its terms. The notice contains important information, limitations and restrictions relating to our software, publications, trademarks, third-party resources, and forward-looking statements. By accessing any of our repositories and other materials, you accept and agree to the terms of the notice.
