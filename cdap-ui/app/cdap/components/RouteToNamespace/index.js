/*
 * Copyright © 2016 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import React, {Component} from 'react';
import find from 'lodash/find';
import Store from 'services/store/store';
import Redirect from 'react-router/Redirect';

export default class RouteToNamespace extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedNamespace: null
    };
  }

  componentWillMount() {
    this.setNamespace();
    this.sub = Store.subscribe(this.setNamespace.bind(this));
  }

  componentWillUnmount() {
    this.sub();
  }

  findNamespace(list, name) {
    return find(list, {name: name});
  }

  setNamespace() {
    let list = Store.getState().namespaces;

    if (!list || list.length === 0) { return;  }

    /**
     * 1. Check if cookie exist, if not, check default
     * 2. Check existence of such namespace, if not take first one from the list
     **/

    let namespaceCookie = localStorage.getItem('NS');

    let selectedNamespace;

    if (namespaceCookie) {
      selectedNamespace = this.findNamespace(list, namespaceCookie);
    }

    if (!selectedNamespace) {
      selectedNamespace = this.findNamespace(list, 'default');
    }

    if (!selectedNamespace) {
      selectedNamespace = list[0].name;
    } else {
      selectedNamespace = selectedNamespace.name;
    }

    localStorage.setItem('NS', selectedNamespace);
    this.setState({selectedNamespace});
  }

  render() {
    if (!this.state.selectedNamespace) {
      return null;
    }
    return <Redirect to={`/ns/${this.state.selectedNamespace}`} />;
  }
}
