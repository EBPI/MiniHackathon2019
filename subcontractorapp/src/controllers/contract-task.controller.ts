/* eslint-disable @typescript-eslint/no-explicit-any */
import { operation, param, requestBody } from '@loopback/rest';
import { ContractTaskCreate } from '../models/contract-task-create.model';
import { ContractTask } from '../models/contract-task.model';
import { ResponseMessage } from '../models/response-message.model';
import { PowerMeter } from '../models/power-meter.model';
import { BlockChainModule } from '../blockchainClient';
let blockchainClient = new BlockChainModule.BlockchainClient();
/**
 * The controller class is generated from OpenAPI spec with operations tagged
 * by ContractTaskController
 *
 */
export class ContractTaskController {
  constructor() { }

  /**
   *
   *

   * @param _requestBody
   * @returns Added new ContractTask
   */
  @operation('post', '/ContractTask/create')
  async CreateContractTask(@requestBody() _requestBody: ContractTaskCreate): Promise<ResponseMessage> {
    try {
      let networkObj = await blockchainClient.connectToNetwork();
      if (networkObj && !(networkObj.stack)) {
        // Construct data object that serves as
        let inputObj = {
          function: 'createContractTask',
          contract: networkObj.contractTasksContract,
          taskId: _requestBody.taskId,
          creationDate: _requestBody.creationDate,
          description: _requestBody.description,
          dueDate: _requestBody.dueDate,
          meterId: "",
          taskStatus: "TODO",
          taskType: _requestBody.taskType,
          contractorId: "",
          verifierId: ""
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
  @operation('get', '/ContractTask/{taskid}')
  async FindContractTaskById(@param({ name: 'taskid', in: 'path' }) taskid: string): Promise<ContractTask> {
    try {
      let networkObj = await blockchainClient.connectToNetwork();
      if (networkObj && !(networkObj.stack)) {
        let inputObj = {
          function: 'readContractTask',
          contract: networkObj.contractTasksContract,
          id: taskid
        };
        return await blockchainClient.queryObjectById(inputObj);
      } else {
        // Couldn't connect to network, so passing this object on to catch clause...
        throw new Error(networkObj.message);
      }
    } catch (error) {
      return error;
    }
  }

  /**
  *
  *

  * @param taskid
  * @param contractorid
  * @returns Updated ContractTask model instance
  */
  @operation('post', '/ContractTask/{taskid}/claim/{contractorid}')
  async ClaimContractTask(@param({ name: 'taskid', in: 'path' }) taskid: string, @param({ name: 'contractorid', in: 'path' }) contractorid: string): Promise<ResponseMessage> {
    try {
      let networkObj = await blockchainClient.connectToNetwork();
      if (networkObj && !(networkObj.stack)) {
        // Todo: find way to convert and read to ContractTask or define multiple methods
        let contractTask = await this.FindContractTaskById(taskid);

        let updateObj = {
          function: 'updateContractTask',
          contract: networkObj.contractTasksContract,
          taskId: contractTask.id,
          creationDate: contractTask.creationDate,
          description: contractTask.description,
          dueDate: contractTask.dueDate,
          meterId: contractTask.meterId,
          taskStatus: "CLAIMED",
          taskType: contractTask.type,
          contractorId: contractorid,
          verifierId: contractTask.verifierId
        };
        await blockchainClient.updateContractTask(updateObj);
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
  @operation('post', '/ContractTask/{taskid}/PowerMeter/mark')
  async ContractTask(@requestBody() _requestBody: PowerMeter, @param({ name: 'taskid', in: 'path' }) taskid: string): Promise<ResponseMessage> {
    try {
      let networkObj = await blockchainClient.connectToNetwork();
      if (networkObj && !(networkObj.stack)) {
        let contractTask = await this.FindContractTaskById(taskid);

        let createMeterObj = {
          function: 'createMeter',
          contract: networkObj.meterContract,
          meterId: _requestBody.id,
          installDate: _requestBody.installDate,
          location: _requestBody.location,
          meterReadings: _requestBody.meterReadings,
          model: _requestBody.model
        }
        await blockchainClient.createMeter(createMeterObj);

        let updateTaskObj = {
          function: 'updateContractTask',
          contract: networkObj.contractTasksContract,
          taskId: contractTask.id,
          creationDate: contractTask.creationDate,
          description: contractTask.description,
          dueDate: contractTask.dueDate,
          meterId: _requestBody.id,
          taskStatus: "AWAITING VERIFICATION",
          taskType: contractTask.type,
          contractorId: contractTask.contractorId,
          verifierId: contractTask.verifierId
        };
        await blockchainClient.updateContractTask(updateTaskObj);
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
  async VerifyContractTask(@param({ name: 'taskid', in: 'path' }) taskid: string, @param({ name: 'verifierid', in: 'path' }) verifierid: string): Promise<ResponseMessage> {
    try {
      let networkObj = await blockchainClient.connectToNetwork();
      if (networkObj && !(networkObj.stack)) {
        let contractTask = await this.FindContractTaskById(taskid);

        let updateTaskObj = {
          function: 'updateContractTask',
          contract: networkObj.contractTasksContract,
          taskId: contractTask.id,
          creationDate: contractTask.creationDate,
          description: contractTask.description,
          dueDate: contractTask.dueDate,
          meterId: contractTask.meterId,
          taskStatus: "DONE",
          taskType: contractTask.type,
          contractorId: contractTask.contractorId,
          verifierId: verifierid
        };
        await blockchainClient.updateContractTask(updateTaskObj);
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

   * @param meterid
   * @returns Get meter info from ContractTask model instances
   */
  @operation('get', '/PowerMeter/{meterid}')
  async FindMeterById(@param({ name: 'meterid', in: 'path' }) meterid: string): Promise<PowerMeter> {
    try {
      let networkObj = await blockchainClient.connectToNetwork();
      if (networkObj && !(networkObj.stack)) {
        let inputObj = {
          function: 'readMeter',
          contract: networkObj.meterContract,
          id: meterid
        };
        return await blockchainClient.queryObjectById(inputObj);
      } else {
        // Couldn't connect to network, so passing this object on to catch clause...
        throw new Error(networkObj.message);
      }
    } catch (error) {
      return error;
    }
  }
}

