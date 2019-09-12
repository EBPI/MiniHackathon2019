const { FileSystemWallet, Gateway } = require('fabric-network');
const fs = require('fs');
const path = require('path');

const configPath = path.join(process.cwd(), './local_fabric/config.json');
const configJSON = fs.readFileSync(configPath, 'utf8');
const config = JSON.parse(configJSON);

let appAdmin = config.appAdmin;
let gatewayDiscovery = config.gatewayDiscovery;
let connection_file = config.connection_file;


// connect to the connection file
const ccpPath = path.join(process.cwd(), './local_fabric/' + connection_file);
const ccpJSON = fs.readFileSync(ccpPath, 'utf8');
const ccp = JSON.parse(ccpJSON);

// A wallet stores a collection of identities for use
const wallet = new FileSystemWallet('./local_fabric/wallet');

// Gateway to interact with the blockchain network
const gateway = new Gateway();

export module BlockChainModule {

  export class BlockchainClient {
    async connectToNetwork() {

      try {
        await gateway.connect(ccp, { wallet, identity: appAdmin, discovery: gatewayDiscovery });

        // Connect to our local fabric
        const network = await gateway.getNetwork('mychannel');

        console.log('Connected to mychannel. ');

        // Get the contract we have installed on the peer
        const contract = await network.getContract('global-citizen');

        let networkObj = {
          contract: contract,
          network: network
        };

        return networkObj;

      } catch (error) {
        console.log(`Error processing transaction. ${error}`);
        console.log(error.stack);

        return error;
      }
    }

    async disconnectFromNetwork() {
      // Properly disconnecting from the network when finished
      let response = await gateway.disconnect();
      return response;
    }

    async createContractTask(args: any) {
      // Calling transaction1 transaction in the smart contract
      let response = await args.contract.submitTransaction(args.function, args.taskId, args.description, args.creationDate, args.dueDate, args.taskType, args.taskStatus, args.contractorId, args.verifierId, args.meterId);

      return response;
    }

    async sendPledgeToGlobalCitizen(args: any) {
      // Calling transaction1 transaction in the smart contract
      let response = await args.contract.submitTransaction(args.function, args.pledgeId);

      return response;
    }

    async sendPledgeToGovOrg(args: any) {
      // Calling transaction1 transaction in the smart contract
      let response = await args.contract.submitTransaction(args.function, args.pledgeId);

      return response;
    }

    async updatePledge(args: any) {
      // Calling transaction1 transaction in the smart contract
      let response = await args.contract.submitTransaction(args.function, args.pledgeId, args.fundingType, args.approvedFunding, args.fundsPerInstallment);

      return response;
    }

    async transferFunds(args: any) {
      // Calling transaction1 transaction in the smart contract
      let response = await args.contract.submitTransaction(args.function, args.pledgeId);

      return response;
    }

    async queryProject(args: any) {
      // Calling transaction1 transaction in the smart contract
      let response = await args.contract.evaluateTransaction(args.function, args.id);
      console.log(JSON.parse(response));
      return JSON.parse(response);
    }
  }
}
