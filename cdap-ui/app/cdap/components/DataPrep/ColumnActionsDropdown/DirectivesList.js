/*
 * Copyright © 2017 Cask Data, Inc.
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

import Loadable from 'react-loadable';
import LoadingSVGCentered from 'components/LoadingSVGCentered';

// Directives List
export const ParseDirective = Loadable({
  loader: () => import(/* webpackChunkName: "Directives_Parse" */ 'components/DataPrep/Directives/Parse'),
  loading: LoadingSVGCentered
});
export const FillNullOrEmpty = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_FillNullOrEmpty" */ 'components/DataPrep/Directives/FillNullOrEmpty'),
  loading: LoadingSVGCentered
});
export const DropColumnDirective = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_DropColumn" */ 'components/DataPrep/Directives/DropColumn'),
  loading: LoadingSVGCentered
});
export const KeepColumnDirective = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_KeepColumn" */ 'components/DataPrep/Directives/KeepColumn'),
  loading: LoadingSVGCentered
});
export const SwapColumnsDirective = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_SwapColumns" */ 'components/DataPrep/Directives/SwapColumns'),
  loading: LoadingSVGCentered
});
export const MergeColumnsDirective = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_MergeColumns" */ 'components/DataPrep/Directives/MergeColumns'),
  loading: LoadingSVGCentered
});
export const FilterDirective = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_Filter" */ 'components/DataPrep/Directives/Filter'),
  loading: LoadingSVGCentered
});
export const FindAndReplaceDirective = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_FindAndReplace" */ 'components/DataPrep/Directives/FindAndReplace'),
  loading: LoadingSVGCentered
});
export const CopyColumnDirective = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_CopyColumn" */ 'components/DataPrep/Directives/CopyColumn'),
  loading: LoadingSVGCentered
});
export const ExtractFields = Loadable({
  loader: import(/* webpackChunkName: "Directive_ExtractFields" */ 'components/DataPrep/Directives/ExtractFields'),
  loading: LoadingSVGCentered
});
export const Format = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_Format"*/'components/DataPrep/Directives/Format'),
  loading: LoadingSVGCentered
});
export const Calculate = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_Calculate"*/'components/DataPrep/Directives/Calculate'),
  loading: LoadingSVGCentered
});
export const Explode = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_Explode"*/'components/DataPrep/Directives/Explode'),
  loading: LoadingSVGCentered
});
export const MaskData = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_MaskData"*/'components/DataPrep/Directives/MaskData'),
  loading: LoadingSVGCentered
});
export const EncodeDecode = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_EncodeDecode"*/'components/DataPrep/Directives/EncodeDecode'),
  loading: LoadingSVGCentered
});
export const Decode = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_Decode"*/'components/DataPrep/Directives/Decode'),
  loading: LoadingSVGCentered
});
export const SetCharacterEncoding = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_SetCharacterEncoding"*/'components/DataPrep/Directives/SetCharacterEncoding'),
  loading: LoadingSVGCentered
});
export const MarkAsError = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_MarkAsError"*/'components/DataPrep/Directives/MarkAsError'),
  loading: LoadingSVGCentered
});
export const CustomTransform = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_CustomTransform"*/'components/DataPrep/Directives/CustomTransform'),
  loading: LoadingSVGCentered
});
export const DefineVariableDirective = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_DefineVariable"*/'components/DataPrep/Directives/DefineVariable'),
  loading: LoadingSVGCentered
});
export const SetCounterDirective = Loadable({
  loader: () => import(/* webpackChunkName: "Directive_SetCounter"*/'components/DataPrep/Directives/SetCounter'),
  loading: LoadingSVGCentered
});
