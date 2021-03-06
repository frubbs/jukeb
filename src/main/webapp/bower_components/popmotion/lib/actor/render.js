'use strict';

var valueTypeManager = require('../value-types/manager');
var each = require('../inc/utils').each;

var createMapper = function (role, mappedValues) {
    return function (name, val) {
        return mappedValues[role.map(name)] = val;
    };
};

module.exports = function (actor) {
    var numActiveParents = actor.activeParents.length;
    var numRoles = actor.roles.length;

    // Update parent values from calculated children
    for (var i = 0; i < numActiveParents; i++) {
        var key = actor.activeParents[i];
        var value = actor.values[key];

        // Update parent value current property
        value.current = valueTypeManager[value.type].combine(actor.state[key], value.template);

        // Update state
        actor.state.values[key] = value.current;
    }

    // Fire `frame` and `update` callbacks on all Roles
    for (var i = 0; i < numRoles; i++) {
        var role = actor.roles[i];
        var mappedValues = {};

        each(actor.state.values, createMapper(role, mappedValues));

        if (role.frame) {
            role.frame.call(actor, mappedValues, actor);
        }

        if (role.update && actor.hasChanged) {
            role.update.call(actor, mappedValues, actor);
        }
    }
};
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi4uLy4uL3NyYy9hY3Rvci9yZW5kZXIuanMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7QUFBQSxJQUFNLGdCQUFnQixHQUFHLE9BQU8sQ0FBQyx3QkFBd0IsQ0FBQyxDQUFDO0FBQzNELElBQU0sSUFBSSxHQUFHLE9BQU8sQ0FBQyxjQUFjLENBQUMsQ0FBQyxJQUFJLENBQUM7O0FBRTFDLElBQU0sWUFBWSxHQUFHLFVBQUMsSUFBSSxFQUFFLFlBQVk7V0FBSyxVQUFDLElBQUksRUFBRSxHQUFHO2VBQUssWUFBWSxDQUFDLElBQUksQ0FBQyxHQUFHLENBQUMsSUFBSSxDQUFDLENBQUMsR0FBRyxHQUFHO0tBQUE7Q0FBQSxDQUFDOztBQUUvRixNQUFNLENBQUMsT0FBTyxHQUFHLFVBQUMsS0FBSyxFQUFLO0FBQ3hCLFFBQU0sZ0JBQWdCLEdBQUcsS0FBSyxDQUFDLGFBQWEsQ0FBQyxNQUFNLENBQUM7QUFDcEQsUUFBTSxRQUFRLEdBQUcsS0FBSyxDQUFDLEtBQUssQ0FBQyxNQUFNOzs7QUFBQyxBQUdwQyxTQUFLLElBQUksQ0FBQyxHQUFHLENBQUMsRUFBRSxDQUFDLEdBQUcsZ0JBQWdCLEVBQUUsQ0FBQyxFQUFFLEVBQUU7QUFDdkMsWUFBSSxHQUFHLEdBQUcsS0FBSyxDQUFDLGFBQWEsQ0FBQyxDQUFDLENBQUMsQ0FBQztBQUNqQyxZQUFJLEtBQUssR0FBRyxLQUFLLENBQUMsTUFBTSxDQUFDLEdBQUcsQ0FBQzs7O0FBQUMsQUFHOUIsYUFBSyxDQUFDLE9BQU8sR0FBRyxnQkFBZ0IsQ0FBQyxLQUFLLENBQUMsSUFBSSxDQUFDLENBQUMsT0FBTyxDQUFDLEtBQUssQ0FBQyxLQUFLLENBQUMsR0FBRyxDQUFDLEVBQUUsS0FBSyxDQUFDLFFBQVEsQ0FBQzs7O0FBQUMsQUFHdkYsYUFBSyxDQUFDLEtBQUssQ0FBQyxNQUFNLENBQUMsR0FBRyxDQUFDLEdBQUcsS0FBSyxDQUFDLE9BQU8sQ0FBQztLQUMzQzs7O0FBQUEsQUFHRCxTQUFLLElBQUksQ0FBQyxHQUFHLENBQUMsRUFBRSxDQUFDLEdBQUcsUUFBUSxFQUFFLENBQUMsRUFBRSxFQUFFO0FBQy9CLFlBQUksSUFBSSxHQUFHLEtBQUssQ0FBQyxLQUFLLENBQUMsQ0FBQyxDQUFDLENBQUM7QUFDMUIsWUFBSSxZQUFZLEdBQUcsRUFBRSxDQUFDOztBQUV0QixZQUFJLENBQUMsS0FBSyxDQUFDLEtBQUssQ0FBQyxNQUFNLEVBQUUsWUFBWSxDQUFDLElBQUksRUFBRSxZQUFZLENBQUMsQ0FBQyxDQUFDOztBQUUzRCxZQUFJLElBQUksQ0FBQyxLQUFLLEVBQUU7QUFDWixnQkFBSSxDQUFDLEtBQUssQ0FBQyxJQUFJLENBQUMsS0FBSyxFQUFFLFlBQVksRUFBRSxLQUFLLENBQUMsQ0FBQztTQUMvQzs7QUFFRCxZQUFJLElBQUksQ0FBQyxNQUFNLElBQUksS0FBSyxDQUFDLFVBQVUsRUFBRTtBQUNqQyxnQkFBSSxDQUFDLE1BQU0sQ0FBQyxJQUFJLENBQUMsS0FBSyxFQUFFLFlBQVksRUFBRSxLQUFLLENBQUMsQ0FBQztTQUNoRDtLQUNKO0NBQ0osQ0FBQSIsImZpbGUiOiJyZW5kZXIuanMiLCJzb3VyY2VzQ29udGVudCI6WyJjb25zdCB2YWx1ZVR5cGVNYW5hZ2VyID0gcmVxdWlyZSgnLi4vdmFsdWUtdHlwZXMvbWFuYWdlcicpO1xuY29uc3QgZWFjaCA9IHJlcXVpcmUoJy4uL2luYy91dGlscycpLmVhY2g7XG5cbmNvbnN0IGNyZWF0ZU1hcHBlciA9IChyb2xlLCBtYXBwZWRWYWx1ZXMpID0+IChuYW1lLCB2YWwpID0+IG1hcHBlZFZhbHVlc1tyb2xlLm1hcChuYW1lKV0gPSB2YWw7XG5cbm1vZHVsZS5leHBvcnRzID0gKGFjdG9yKSA9PiB7XG4gICAgY29uc3QgbnVtQWN0aXZlUGFyZW50cyA9IGFjdG9yLmFjdGl2ZVBhcmVudHMubGVuZ3RoO1xuICAgIGNvbnN0IG51bVJvbGVzID0gYWN0b3Iucm9sZXMubGVuZ3RoO1xuXG4gICAgLy8gVXBkYXRlIHBhcmVudCB2YWx1ZXMgZnJvbSBjYWxjdWxhdGVkIGNoaWxkcmVuXG4gICAgZm9yIChsZXQgaSA9IDA7IGkgPCBudW1BY3RpdmVQYXJlbnRzOyBpKyspIHtcbiAgICAgICAgbGV0IGtleSA9IGFjdG9yLmFjdGl2ZVBhcmVudHNbaV07XG4gICAgICAgIGxldCB2YWx1ZSA9IGFjdG9yLnZhbHVlc1trZXldO1xuXG4gICAgICAgIC8vIFVwZGF0ZSBwYXJlbnQgdmFsdWUgY3VycmVudCBwcm9wZXJ0eVxuICAgICAgICB2YWx1ZS5jdXJyZW50ID0gdmFsdWVUeXBlTWFuYWdlclt2YWx1ZS50eXBlXS5jb21iaW5lKGFjdG9yLnN0YXRlW2tleV0sIHZhbHVlLnRlbXBsYXRlKTtcblxuICAgICAgICAvLyBVcGRhdGUgc3RhdGVcbiAgICAgICAgYWN0b3Iuc3RhdGUudmFsdWVzW2tleV0gPSB2YWx1ZS5jdXJyZW50O1xuICAgIH1cblxuICAgIC8vIEZpcmUgYGZyYW1lYCBhbmQgYHVwZGF0ZWAgY2FsbGJhY2tzIG9uIGFsbCBSb2xlc1xuICAgIGZvciAobGV0IGkgPSAwOyBpIDwgbnVtUm9sZXM7IGkrKykge1xuICAgICAgICBsZXQgcm9sZSA9IGFjdG9yLnJvbGVzW2ldO1xuICAgICAgICBsZXQgbWFwcGVkVmFsdWVzID0ge307XG5cbiAgICAgICAgZWFjaChhY3Rvci5zdGF0ZS52YWx1ZXMsIGNyZWF0ZU1hcHBlcihyb2xlLCBtYXBwZWRWYWx1ZXMpKTtcblxuICAgICAgICBpZiAocm9sZS5mcmFtZSkge1xuICAgICAgICAgICAgcm9sZS5mcmFtZS5jYWxsKGFjdG9yLCBtYXBwZWRWYWx1ZXMsIGFjdG9yKTtcbiAgICAgICAgfVxuXG4gICAgICAgIGlmIChyb2xlLnVwZGF0ZSAmJiBhY3Rvci5oYXNDaGFuZ2VkKSB7XG4gICAgICAgICAgICByb2xlLnVwZGF0ZS5jYWxsKGFjdG9yLCBtYXBwZWRWYWx1ZXMsIGFjdG9yKTtcbiAgICAgICAgfVxuICAgIH1cbn0iXX0=