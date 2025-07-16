import React, { Component } from 'react';
import { connect } from 'react-redux';
import { setCustomURL, setSelectDropDown } from './store';

class MyFormComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      localURL: props.customURL,
      localDropDown: props.selectDropDown,
    };
  }

  handleURLChange = (e) => {
    const value = e.target.value;
    this.setState({ localURL: value });
    this.props.setCustomURL(value);
  };

  handleDropDownChange = (e) => {
    const value = e.target.value;
    this.setState({ localDropDown: value });
    this.props.setSelectDropDown(value);
  };

  render() {
    const { localURL, localDropDown } = this.state;

    return (
      <div style={{ padding: '1rem', fontFamily: 'Arial' }}>
        <h3>URL & Dropdown Form</h3>

        <div style={{ marginBottom: '1rem' }}>
          <label>Custom URL:</label>
          <input
            type="text"
            value={localURL}
            onChange={this.handleURLChange}
            style={{ marginLeft: '1rem', width: '300px' }}
          />
        </div>

        <div>
          <label>Select Option:</label>
          <select
            value={localDropDown}
            onChange={this.handleDropDownChange}
            style={{ marginLeft: '1rem' }}
          >
            <option value="Option1">Option 1</option>
            <option value="Option2">Option 2</option>
            <option value="Option3">Option 3</option>
          </select>
        </div>
      </div>
    );
  }
}

// Redux mapping
const mapStateToProps = (state) => ({
  customURL: state.customURL,
  selectDropDown: state.selectDropDown,
});

const mapDispatchToProps = {
  setCustomURL,
  setSelectDropDown,
};

export default connect(mapStateToProps, mapDispatchToProps)(MyFormComponent);
