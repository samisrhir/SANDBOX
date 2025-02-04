import React, { Component } from "react";
import PropTypes from "prop-types";
import ImPropTypes from "react-immutable-proptypes";

export default class ModelWrapper extends Component {
  static propTypes = {
    schema: PropTypes.object.isRequired,
    name: PropTypes.string,
    displayName: PropTypes.string,
    fullPath: PropTypes.array.isRequired,
    specPath: ImPropTypes.list.isRequired,
    getComponent: PropTypes.func.isRequired,
    getConfigs: PropTypes.func.isRequired,
    specSelectors: PropTypes.object.isRequired,
    expandDepth: PropTypes.number,
    layoutActions: PropTypes.object,
    layoutSelectors: PropTypes.object.isRequired,
    includeReadOnly: PropTypes.bool,
    includeWriteOnly: PropTypes.bool,
    customExample: PropTypes.object, // Add customExample prop
  };

  onToggle = (name, isShown) => {
    // If this prop is present, we'll have deepLinking for it
    if (this.props.layoutActions) {
      this.props.layoutActions.show(this.props.fullPath, isShown);
    }
  };

  render() {
    let { getComponent, getConfigs, customExample } = this.props; // Destructure customExample
    const Model = getComponent("Model");

    let expanded;
    if (this.props.layoutSelectors) {
      // If this is prop is present, we'll have deepLinking for it
      expanded = this.props.layoutSelectors.isShown(this.props.fullPath);
    }

    return (
      <div className="model-box">
        <Model
          {...this.props}
          getConfigs={getConfigs}
          expanded={expanded}
          depth={1}
          onToggle={this.onToggle}
          expandDepth={this.props.expandDepth || 0}
          customExample={customExample} // Pass customExample to Model
        />
      </div>
    );
  }
}
