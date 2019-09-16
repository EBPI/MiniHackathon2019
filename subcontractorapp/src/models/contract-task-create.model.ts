/* eslint-disable @typescript-eslint/no-explicit-any */
import { model, property } from '@loopback/repository';

/**
 * The model class is generated from OpenAPI schema - ContractTask
 * ContractTask
 */
@model({ name: 'ContractTaskCreate' })
export class ContractTaskCreate {
  constructor(data?: Partial<ContractTaskCreate>) {
    if (data != null && typeof data === 'object') {
      Object.assign(this, data);
    }
  }

  /**
   *
   */
  @property({ required: true })
  taskId: string;

  /**
   *
   */
  @property({ required: true })
  description: string;

  /**
   *
   */
  @property({ required: true })
  creationDate: string;

  /**
   *
   */
  @property({ required: true })
  dueDate: string;

  /**
   *
   */
  @property({ required: true })
  taskType: string;

}

