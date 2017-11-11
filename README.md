# Web3j Camel component for Ethereum

Start up an Ethereum client if you don't already have one running, such as Geth:

```
$ geth --rpcapi personal,db,eth,net,web3 --rpc --testnet --cache=4096 

```
From another terminal attach a client and verify the node has completed suynced  

```
$ geth attach http://localhost:8545

```
eth.syncing  // should return false before running tests