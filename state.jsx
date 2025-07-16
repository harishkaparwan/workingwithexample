import { createStore } from 'redux';

// Initial State
const initialState = {
  customURL: '',
  selectDropDown: 'Option1',
};

// Action Types
const SET_CUSTOM_URL = 'SET_CUSTOM_URL';
const SET_SELECT_DROPDOWN = 'SET_SELECT_DROPDOWN';

// Action Creators
export const setCustomURL = (value) => ({
  type: SET_CUSTOM_URL,
  payload: value,
});

export const setSelectDropDown = (value) => ({
  type: SET_SELECT_DROPDOWN,
  payload: value,
});

// Reducer
function reducer(state = initialState, action) {
  switch (action.type) {
    case SET_CUSTOM_URL:
      return { ...state, customURL: action.payload };
    case SET_SELECT_DROPDOWN:
      return { ...state, selectDropDown: action.payload };
    default:
      return state;
  }
}

export const store = createStore(reducer);
