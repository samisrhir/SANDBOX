import React, { PureComponent } from "react";
import PropTypes from "prop-types";
import { fromJS, List } from "immutable";
import { getKnownSyntaxHighlighterLanguage } from "core/utils/jsonParse";
import createHtmlReadyId from "core/utils/create-html-ready-id";

const NOOP = Function.prototype;

export default class ParamBody extends PureComponent {
  static propTypes = {
    param: PropTypes.object,
    onChange: PropTypes.func,
    onChangeConsumes: PropTypes.func,
    consumes: PropTypes.object,
    consumesValue: PropTypes.string,
    fn: PropTypes.object.isRequired,
    getComponent: PropTypes.func.isRequired,
    isExecute: PropTypes.bool,
    specSelectors: PropTypes.object.isRequired,
    pathMethod: PropTypes.array.isRequired,
    selectedScenarioBody: PropTypes.string,
    defaultBody: PropTypes.string,
  };

  static defaultProps = {
    consumes: fromJS(["application/json"]),
    param: fromJS({}),
    onChange: NOOP,
    onChangeConsumes: NOOP,
  };

  constructor(props, context) {
    super(props, context);

    this.state = {
      isEditBox: false,
      value: props.defaultBody || "",
    };
  }

  componentDidMount() {
    this.updateValues(this.props);
    // Listen for the custom event dispatched by ScenarioSelector
    document.addEventListener("scenarioChange", this.handleScenarioChange);
  }

  componentWillUnmount() {
    // Remove event listener to prevent memory leaks
    document.removeEventListener("scenarioChange", this.handleScenarioChange);
  }

  UNSAFE_componentWillReceiveProps(nextProps) {
    this.updateValues(nextProps);
  }

  updateValues = (props) => {
    let { param, isExecute, consumesValue = "", selectedScenarioBody } = props;
    let isXml = /xml/i.test(consumesValue);
    let isJson = /json/i.test(consumesValue);
    let paramValue = isXml ? param.get("value_xml") : param.get("value");

    if (selectedScenarioBody) {
      this.setState({ value: selectedScenarioBody });
    } else if (paramValue !== undefined) {
      let val = !paramValue && isJson ? "{}" : paramValue;
      this.setState({ value: val });
      this.onChange(val, { isXml: isXml, isEditBox: isExecute });
    } else {
      if (isXml) {
        this.onChange(this.sample("xml"), { isXml: isXml, isEditBox: isExecute });
      } else {
        this.onChange(this.sample(), { isEditBox: isExecute });
      }
    }
  };

  sample = (xml) => {
    let { param, fn } = this.props;
    let schema = fn.inferSchema(param.toJS());

    return fn.getSampleSchema(schema, xml, {
      includeWriteOnly: true,
    });
  };

  onChange = (value, { isEditBox, isXml }) => {
    this.setState({ value, isEditBox });
    this.props.onChange(value);
  };

  handleScenarioChange = (event) => {
    const selectedScenarioBody = event.detail.body;
    this.setState({ value: selectedScenarioBody });
    this.props.onChange(selectedScenarioBody);
  };

  handleOnChange = (e) => {
    const { consumesValue } = this.props;
    const isXml = /xml/i.test(consumesValue);
    const inputValue = e.target.value;

    this.onChange(inputValue, { isXml, isEditBox: this.state.isEditBox });
  };

  toggleIsEditBox = () => this.setState((state) => ({ isEditBox: !state.isEditBox }));

  render() {
    let {
      onChangeConsumes,
      param,
      isExecute,
      specSelectors,
      pathMethod,
      getComponent,
      selectedScenarioBody,
    } = this.props;

    const Button = getComponent("Button");
    const TextArea = getComponent("TextArea");
    const HighlightCode = getComponent("HighlightCode", true);
    const ContentType = getComponent("contentType");
    let parameter = specSelectors ? specSelectors.parameterWithMetaByIdentity(pathMethod, param) : param;
    let errors = parameter.get("errors", List());
    let consumesValue = specSelectors.contentTypeValues(pathMethod).get("requestContentType");
    let consumes = this.props.consumes && this.props.consumes.size ? this.props.consumes : ParamBody.defaultProps.consumes;

    let { value, isEditBox } = this.state;
    let language = null;
    let testValueForJson = getKnownSyntaxHighlighterLanguage(value);
    if (testValueForJson) {
      language = "json";
    }

    const regionId = createHtmlReadyId(`${pathMethod[1]}${pathMethod[0]}_parameters`);
    const controlId = `${regionId}_select`;

    return (
      <div>
        {/* Render the ScenarioSelector component */}
        {/* <ScenarioSelector onScenarioChange={(selectedScenarioBody) => this.onChange(selectedScenarioBody, { isEditBox: this.state.isEditBox })} /> */}

        <div className="body-param" data-param-name={param.get("name")} data-param-in={param.get("in")}>
          {isEditBox && isExecute ? (
            <TextArea className={"body-param__text" + (errors.count() ? " invalid" : "")} value={selectedScenarioBody || value} onChange={this.handleOnChange} />
          ) : (
            (selectedScenarioBody || value) && <HighlightCode className="body-param__example" language={language}>{selectedScenarioBody || value}</HighlightCode>
          )}
          <div className="body-param-options">
            {!isExecute ? null : (
              <div className="body-param-edit">
                <Button className={isEditBox ? "btn cancel body-param__example-edit" : "btn edit body-param__example-edit"} onClick={this.toggleIsEditBox}>
                  {isEditBox ? "Cancel" : "Edit"}
                </Button>
              </div>
            )}
            <label htmlFor={controlId}>
              <span>Parameter content type</span>
              <ContentType
                value={consumesValue}
                contentTypes={consumes}
                onChange={onChangeConsumes}
                className="body-param-content-type"
                ariaLabel="Parameter content type"
                controlId={controlId}
              />
            </label>
          </div>
        </div>
      </div>
    );
  }
}
