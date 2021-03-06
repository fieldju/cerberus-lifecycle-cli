/*
 * Copyright (c) 2016 Nike, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nike.cerberus.domain.cloudformation;

/**
 * CloudFormation input parameters common to all Cerberus CloudFormation stacks.
 */
public class TagParametersDelegate {

    private String tagName;

    private String tagEmail;

    private String tagCostcenter;

    public String getTagName() {
        return tagName;
    }

    public TagParametersDelegate setTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }

    public String getTagEmail() {
        return tagEmail;
    }

    public TagParametersDelegate setTagEmail(String tagEmail) {
        this.tagEmail = tagEmail;
        return this;
    }

    public String getTagCostcenter() {
        return tagCostcenter;
    }

    public TagParametersDelegate setTagCostcenter(String tagCostcenter) {
        this.tagCostcenter = tagCostcenter;
        return this;
    }
}
