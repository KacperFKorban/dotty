class FilterGroup extends Component {
  constructor(props) {
    super(props);

    this.filterToggleRef = findRef(".filterToggleButton");
    this.filterLowerContainerRef = findRef(".filterLowerContainer");

    withEvent(
      this.filterToggleRef,
      "click",
      this.props.onFilterVisibilityChange
    );

    this.render(this.props);
  }

  onFilterClick = ({
    currentTarget: {
      dataset: { key, value },
    },
  }) => {
    this.props.onFilterToggle(key, value);
  };

  onSelectAllClick = ({
    currentTarget: {
      dataset: { key },
    },
  }) => {
    this.props.onGroupSelectChange(key, true);
  };

  onDeselectAllClick = ({
    currentTarget: {
      dataset: { key },
    },
  }) => {
    this.props.onGroupSelectChange(key, false);
  };

  attachFiltersClicks() {
    const refs = findRefs(
      "button.filterButtonItem",
      this.filterLowerContainerRef
    );
    attachListeners(refs, "click", this.onFilterClick);
  }

  attachSelectingButtonsClicks() {
    const selectAllRefs = findRefs(
      "button.selectAll",
      this.filterLowerContainerRef
    );
    const deselectAllRefs = findRefs(
      "button.deselectAll",
      this.filterLowerContainerRef
    );

    attachListeners(selectAllRefs, "click", this.onSelectAllClick);
    attachListeners(deselectAllRefs, "click", this.onDeselectAllClick);
  }

  isActive(isActive) {
    return isActive ? "active" : "";
  }

  isVisible(visible) {
    return visible ? "visible" : "";
  }

  getSortedValues(filterKey, values) {
    const defaultFilterKey = `${filterKey.charAt(1).toLowerCase()}${filterKey.slice(2)}`
    const defaultGroupFilter = Filter.defaultFilters[defaultFilterKey]

    return Object.entries(values).sort(([a], [b]) => 
      a === defaultGroupFilter || b === defaultGroupFilter ? 1 : a.localeCompare(b)
    )
  }

  getFilterGroup(filterKey, values) {
    return `
      <div class="filterGroup">
        <div class="groupTitle">
          <span>${filterKey.substring(1)}</span>
          <div class="groupButtonsContainer">
            <button class="selectAll" data-key="${filterKey}">Select All</button>
            <button class="deselectAll" data-key="${filterKey}">Deselect All</button>
          </div>
        </div>
        <div class="filterList">
          ${this.getSortedValues(filterKey, values)
            .map(
              ([key, data]) =>
                `<button class="filterButtonItem ${this.isActive(
                  data.selected
                )} ${this.isVisible(
                  data.visible
                )}" data-key="${filterKey}" data-value="${key}">${key}</button>`
            )
            .join(" ")}
        </div>
      </div>
    `;
  }

  render({ filter }) {
    attachDOM(
      this.filterLowerContainerRef,
      Object.entries(filter.filters)
        .filter(([_key, values]) => Object.values(values).some((v) => v.visible))
        .map(([key, values]) => this.getFilterGroup(key, values))
    );

    this.attachFiltersClicks();
    this.attachSelectingButtonsClicks();
  }
}
