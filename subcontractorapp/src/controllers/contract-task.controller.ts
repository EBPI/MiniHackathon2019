/* eslint-disable @typescript-eslint/no-explicit-any */
import {operation, param, requestBody} from '@loopback/rest';
import {ContractTask} from '../models/contract-task.model';
import {ResponseMessage} from '../models/response-message.model';
import {PowerMeter} from '../models/power-meter.model';
import { BlockChainModule } from '../blockchainClient';
let blockchainClient = new BlockChainModule.BlockchainClient();
/**
 * The controller class is generated from OpenAPI spec with operations tagged
 * by ContractTaskController
 * 
 */
export class ContractTaskController {
  constructor() {}

  /**
   * 
   * 

   * @param _requestBody 
   * @returns Added new ContractTask
   */
  @operation('post', '/ContractTask/create')
  async CreateContractTask(@requestBody() _requestBody: ContractTask): Promise<ResponseMessage> {
    try {
      let networkObj = await blockchainClient.connectToNetwork();
      if (networkObj && !(networkObj.stack)) {
        // Construct data object that serves as
        let inputObj = {
          function: 'createContractTask',
          contract: networkObj.contract,
          taskId: _requestBody.taskId,
          description: _requestBody.description,
          creationDate: _requestBody.creationDate,
          dueDate: _requestBody.dueDate,
          taskType: _requestBody.taskType,
          taskStatus: _requestBody.taskStatus,
          contractorid: _requestBody.contractorId,
          verifierid: _requestBody.verifierId,
          meterid: _requestBody.meterId
        };
        await blockchainClient.createContractTask(inputObj);
      } else {
        // Couldn't connect to network, so passing this object on to catch clause...
        throw new Error(networkObj.message);
      }
      let responseMessage: ResponseMessage = new ResponseMessage({
        message: 'added Task'
      });
      return responseMessage;
    } catch (error) {
      let responseMessage: ResponseMessage = new ResponseMessage({
        message: error, statusCode: '400'
      });
      return responseMessage;
    }
  }

  /**
   * 
   * 

   * @returns List of ContractTask model instances
   */
  @operation('get', '/ContractTask')
  async FindAllContractTasks(): Promise<ContractTask> {
    throw new Error('Not implemented');
  }

  /**
   * 
   * 

   * @param taskid 
   * @param contractorid 
   * @returns Updated ContractTask model instance
   */
  @operation('post', '/ContractTask/{taskid}/claim/{contractorid}')
  async ClaimContractTask(@param({name: 'taskid', in: 'path'}) taskid: string, @param({name: 'contractorid', in: 'path'}) contractorid: string): Promise<ResponseMessage> {
    try {
      let networkObj = await blockchainClient.connectToNetwork();
      if (networkObj && !(networkObj.stack)) {
        // Construct data object that serves as
        let inputObj = {
          function: 'claimContractTask',
          contract: networkObj.contract,
          taskid: taskid,
          contractorid: contractorid
        };
        //await blockchainClient.createContractTask(inputObj);
      } else {
        // Couldn't connect to network, so passing this object on to catch clause...
        throw new Error(networkObj.message);
      }
      let responseMessage: ResponseMessage = new ResponseMessage({
        message: 'added Task to Contractor'
      });
      return responseMessage;
    } catch (error) {
      let responseMessage: ResponseMessage = new ResponseMessage({
        message: error, statusCode: '400'
      });
      return responseMessage;
    }
  }

  /**
   * 
   * 

   * @param _requestBody 
   * @param taskid 
   * @param meterid 
   * @returns Updated ContractTask model instance
   */
  @operation('post', '/ContractTask/{taskid}/PowerMeter/{meterid}/status')
  async StatusContractTask(@requestBody() _requestBody: PowerMeter, @param({name: 'taskid', in: 'path'}) taskid: string, @param({name: 'meterid', in: 'path'}) meterid: string): Promise<ResponseMessage> {
    try {
      let networkObj = await blockchainClient.connectToNetwork();
      if (networkObj && !(networkObj.stack)) {
        // Construct data object that serves as
        let inputObj = {
          function: 'StatusContractTask',
          contract: networkObj.contract,
          taskid: taskid,
          meterid: meterid,
          model: _requestBody.model,
          meterReadings: _requestBody.meterReadings,
          meterLocation: _requestBody.meterLocation,
          installedDate: _requestBody.installedDate
        };
        //await blockchainClient.createContractTask(inputObj);
      } else {
        // Couldn't connect to network, so passing this object on to catch clause...
        throw new Error(networkObj.message);
      }
      let responseMessage: ResponseMessage = new ResponseMessage({
        message: 'added Meter'
      });
      return responseMessage;
    } catch (error) {
      let responseMessage: ResponseMessage = new ResponseMessage({
        message: error, statusCode: '400'
      });
      return responseMessage;
    }
  }

  /**
   * 
   * 

   * @param taskid 
   * @param verifierid 
   * @returns Updated ContractTask model instance
   */
  @operation('post', '/ContractTask/{taskid}/verify/{verifierid}')
  async VerifyContractTask(@param({name: 'taskid', in: 'path'}) taskid: string, @param({name: 'verifierid', in: 'path'}) verifierid: string): Promise<ResponseMessage> {
    try {
      let networkObj = await blockchainClient.connectToNetwork();
      if (networkObj && !(networkObj.stack)) {
        // Construct data object that serves as
        let inputObj = {
          function: 'contractTask',
          contract: networkObj.contract,
          taskId: taskid,
          verifierid: verifierid
        };
        //await blockchainClient.createContractTask(inputObj);
      } else {
        // Couldn't connect to network, so passing this object on to catch clause...
        throw new Error(networkObj.message);
      }
      let responseMessage: ResponseMessage = new ResponseMessage({
        message: 'updated Task Status'
      });
      return responseMessage;
    } catch (error) {
      let responseMessage: ResponseMessage = new ResponseMessage({
        message: error, statusCode: '400'
      });
      return responseMessage;
    }
  }

  /**
   * 
   * 

   * @param taskid 
   * @param meterid 
   * @returns Get meter info from ContractTask model instances
   */
  @operation('get', '/ContractTask/{taskid}/PowerMeter/{meterid}')
  async FindMeterContractTask(@param({name: 'taskid', in: 'path'}) taskid: string, @param({name: 'meterid', in: 'path'}) meterid: string): Promise<PowerMeter> {
    try {
      let networkObj = await blockchainClient.connectToNetwork();
      if (networkObj && !(networkObj.stack)) {
        let inputObj = {
          function: 'readProjectPledge',
          contract: networkObj.contract,
          taskid: taskid,
          meterid: meterid
        };
        return await blockchainClient.queryProject(inputObj);
      } else {
        // Couldn't connect to network, so passing this object on to catch clause...
        throw new Error(networkObj.message);
      }
    } catch (error) {
      return error;
    }
  }

}

