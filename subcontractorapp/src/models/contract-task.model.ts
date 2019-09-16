/* eslint-disable @typescript-eslint/no-explicit-any */
import { model, property } from '@loopback/repository';

/**
 * The model class is generated from OpenAPI schema - ContractTask
 * ContractTask
 */
@model({ name: 'ContractTask' })
export class ContractTask {
  constructor(data?: Partial<ContractTask>) {
    if (data != null && typeof data === 'object') {
      Object.assign(this, data);
    }
  }

  /**
   *
   */
  @property({ required: true })
  id: string;

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
  type: string;

  /**
   *
   */
  @property({ required: true })
  status: string;

  /**
   *
   */
  @property({ required: true })
  contractorId: string;

  /**
   *
   */
  @property({ required: true })
  verifierId: string;

  /**
   *
   */
  @property({ required: true })
  meterId: string;

}

