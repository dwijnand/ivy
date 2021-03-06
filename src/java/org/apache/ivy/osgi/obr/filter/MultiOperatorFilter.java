/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.apache.ivy.osgi.obr.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ivy.osgi.obr.xml.RequirementFilter;

public abstract class MultiOperatorFilter extends RequirementFilter {

    private List/* <RequirementFilter> */subFilters = new ArrayList/* <RequirementFilter> */();

    public MultiOperatorFilter() {
        // default constructor
    }

    public MultiOperatorFilter(RequirementFilter[] filters) {
        for (int i = 0; i < filters.length; i++) {
            RequirementFilter filter = filters[i];
            add(filter);
        }
    }

    abstract protected char operator();

    public void append(StringBuffer builder) {
        builder.append('(');
        builder.append(operator());
        Iterator itSubFilters = subFilters.iterator();
        while (itSubFilters.hasNext()) {
            RequirementFilter filter = (RequirementFilter) itSubFilters.next();
            filter.append(builder);
        }
        builder.append(')');
    }

    public void add(RequirementFilter subFilter2) {
        subFilters.add(subFilter2);
    }

    public List/* <RequirementFilter> */getSubFilters() {
        return subFilters;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        Iterator itSubFilters = subFilters.iterator();
        while (itSubFilters.hasNext()) {
            RequirementFilter subFilter = (RequirementFilter) itSubFilters.next();
            result = prime * result + ((subFilter == null) ? 0 : subFilter.hashCode());
        }
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MultiOperatorFilter)) {
            return false;
        }
        MultiOperatorFilter other = (MultiOperatorFilter) obj;
        if (subFilters == null) {
            if (other.subFilters != null) {
                return false;
            }
        } else if (other.subFilters == null) {
            return false;
        } else if (subFilters.size() != other.subFilters.size()) {
            return false;
        } else if (!subFilters.containsAll(other.subFilters)) {
            return false;
        }
        return true;
    }
}
