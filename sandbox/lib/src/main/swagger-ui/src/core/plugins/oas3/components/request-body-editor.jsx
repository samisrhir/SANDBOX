import React, { PureComponent } from "react";
import PropTypes from "prop-types";
import { stringify } from "core/utils";

const NOOP = Function.prototype;

export default class RequestBodyEditor extends PureComponent {
  static propTypes = {
    onChange: PropTypes.func,
    getComponent: PropTypes.func.isRequired,
    value: PropTypes.string,
    defaultValue: PropTypes.string,
    errors: PropTypes.array,
  };

  static defaultProps = {
    onChange: NOOP,
    userHasEditedBody: false,
  };

  constructor(props, context) {
    super(props, context);

    this.state = {
      value: stringify(props.value) || props.defaultValue,
    };
  }

  componentDidMount() {
    document.addEventListener("scenarioChange", this.handleScenarioChange);
  }

  componentWillUnmount() {
    document.removeEventListener("scenarioChange", this.handleScenarioChange);
  }

  handleScenarioChange = (event) => {
    const newValue = event.detail.body;
    this.setState({ value: newValue });
    this.props.onChange(newValue);
  };

  onChange = (value) => {
    this.props.onChange(stringify(value));
    this.setState({ value });
  };

  render() {
    const { getComponent, errors } = this.props;
    const { value } = this.state;
    const TextArea = getComponent("TextArea");

    return (
      <div className="body-param">
        <TextArea
          className="sami"
          title={errors && errors.size ? errors.join(", ") : ""}
          value={value}
          onChange={(e) => this.onChange(e.target.value)}
        />
      </div>
    );
  }
}
