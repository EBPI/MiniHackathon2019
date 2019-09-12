/* eslint-disable @typescript-eslint/no-explicit-any */
import {model, property} from '@loopback/repository';

/**
 * The model class is generated from OpenAPI schema - PowerMeter
 * PowerMeter
 */
@model({name: 'PowerMeter'})
export class PowerMeter {
  constructor(data?: Partial<PowerMeter>) {
    if (data != null && typeof data === 'object') {
      Object.assign(this, data);
    }
  }

  /**
   * 
   */
  @property({required: true})
  meterId: string;

  /**
   * 
   */
  @property({required: true})
  model: string;

  /**
   * 
   */
  @property({required: true})
  meterReadings: string;

  /**
   * 
   */
  @property({required: true})
  meterLocation: string;

  /**
   * 
   */
  @property({required: true})
  installedDate: string;

}

