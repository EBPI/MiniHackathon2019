/* eslint-disable @typescript-eslint/no-explicit-any */
import {operation, param, requestBody} from '@loopback/rest';
import {ContractTask} from '../models/contract-task.model';
import {ResponseMessage} from '../models/response-message.model';
import {PowerMeter} from '../models/power-meter.model';

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
    throw new Error('Not implemented');
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
    throw new Error('Not implemented');
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
    throw new Error('Not implemented');
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
    throw new Error('Not implemented');
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
    throw new Error('Not implemented');
  }

}

