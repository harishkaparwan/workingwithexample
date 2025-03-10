import {useEffect} from 'react';
import mitt, { Emitter, Handler } from 'mitt';

// Create a generic event emitter type
type GenericEmitter<T> = Emitter<T>;

// Generic hook to listen to events
export function useEventListener<T, EventName extends keyof T>(
  event: EventName,
  handler: Handler<T[EventName]>,
  emitter: GenericEmitter<T>
) {
  useEffect(() => {
    emitter.on(event, handler);
    return () => {
      emitter.off(event, handler);
    };
  }, [event, handler, emitter]);
}

// Generic function to dispatch events
export function dispatchEvent<T, EventName extends keyof T>(
  event: EventName,
  payload: T[EventName],
  emitter: GenericEmitter<T>
) {
  emitter.emit(event, payload);
}

// Factory function to create a new emitter instance
export function createEmitter<T>(): GenericEmitter<T> {
  return mitt<T>();
}