'use strict';

var Role = require('./Role');

module.exports = new Role({
    init: function (actor) {
        if (actor.init) {
            actor.init(actor);
        }
    },

    start: function (actor) {
        if (actor.onStart) {
            actor.onStart(actor);
        }
    },

    frame: function (state, actor) {
        if (actor.onFrame) {
            actor.onFrame(state, actor);
        }
    },

    update: function (state, actor) {
        if (actor.onUpdate) {
            actor.onUpdate(state, actor);
        }
    },

    complete: function (actor) {
        if (actor.onComplete) {
            actor.onComplete(actor);
        }
    }
});
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi4uLy4uL3NyYy9yb2xlcy9kZWZhdWx0Um9sZS5qcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOztBQUFBLElBQUksSUFBSSxHQUFHLE9BQU8sQ0FBQyxRQUFRLENBQUMsQ0FBQzs7QUFFN0IsTUFBTSxDQUFDLE9BQU8sR0FBRyxJQUFJLElBQUksQ0FBQztBQUN0QixRQUFJLEVBQUUsVUFBVSxLQUFLLEVBQUU7QUFDbkIsWUFBSSxLQUFLLENBQUMsSUFBSSxFQUFFO0FBQ1osaUJBQUssQ0FBQyxJQUFJLENBQUMsS0FBSyxDQUFDLENBQUM7U0FDckI7S0FDSjs7QUFFRCxTQUFLLEVBQUUsVUFBVSxLQUFLLEVBQUU7QUFDcEIsWUFBSSxLQUFLLENBQUMsT0FBTyxFQUFFO0FBQ2YsaUJBQUssQ0FBQyxPQUFPLENBQUMsS0FBSyxDQUFDLENBQUM7U0FDeEI7S0FDSjs7QUFFRCxTQUFLLEVBQUUsVUFBVSxLQUFLLEVBQUUsS0FBSyxFQUFFO0FBQzNCLFlBQUksS0FBSyxDQUFDLE9BQU8sRUFBRTtBQUNmLGlCQUFLLENBQUMsT0FBTyxDQUFDLEtBQUssRUFBRSxLQUFLLENBQUMsQ0FBQztTQUMvQjtLQUNKOztBQUVELFVBQU0sRUFBRSxVQUFVLEtBQUssRUFBRSxLQUFLLEVBQUU7QUFDNUIsWUFBSSxLQUFLLENBQUMsUUFBUSxFQUFFO0FBQ2hCLGlCQUFLLENBQUMsUUFBUSxDQUFDLEtBQUssRUFBRSxLQUFLLENBQUMsQ0FBQztTQUNoQztLQUNKOztBQUVELFlBQVEsRUFBRSxVQUFVLEtBQUssRUFBRTtBQUN2QixZQUFJLEtBQUssQ0FBQyxVQUFVLEVBQUU7QUFDbEIsaUJBQUssQ0FBQyxVQUFVLENBQUMsS0FBSyxDQUFDLENBQUM7U0FDM0I7S0FDSjtDQUNKLENBQUMsQ0FBQyIsImZpbGUiOiJkZWZhdWx0Um9sZS5qcyIsInNvdXJjZXNDb250ZW50IjpbInZhciBSb2xlID0gcmVxdWlyZSgnLi9Sb2xlJyk7XG5cbm1vZHVsZS5leHBvcnRzID0gbmV3IFJvbGUoe1xuICAgIGluaXQ6IGZ1bmN0aW9uIChhY3Rvcikge1xuICAgICAgICBpZiAoYWN0b3IuaW5pdCkge1xuICAgICAgICAgICAgYWN0b3IuaW5pdChhY3Rvcik7XG4gICAgICAgIH1cbiAgICB9LFxuXG4gICAgc3RhcnQ6IGZ1bmN0aW9uIChhY3Rvcikge1xuICAgICAgICBpZiAoYWN0b3Iub25TdGFydCkge1xuICAgICAgICAgICAgYWN0b3Iub25TdGFydChhY3Rvcik7XG4gICAgICAgIH1cbiAgICB9LFxuXG4gICAgZnJhbWU6IGZ1bmN0aW9uIChzdGF0ZSwgYWN0b3IpIHtcbiAgICAgICAgaWYgKGFjdG9yLm9uRnJhbWUpIHtcbiAgICAgICAgICAgIGFjdG9yLm9uRnJhbWUoc3RhdGUsIGFjdG9yKTtcbiAgICAgICAgfVxuICAgIH0sXG5cbiAgICB1cGRhdGU6IGZ1bmN0aW9uIChzdGF0ZSwgYWN0b3IpIHtcbiAgICAgICAgaWYgKGFjdG9yLm9uVXBkYXRlKSB7XG4gICAgICAgICAgICBhY3Rvci5vblVwZGF0ZShzdGF0ZSwgYWN0b3IpO1xuICAgICAgICB9XG4gICAgfSxcblxuICAgIGNvbXBsZXRlOiBmdW5jdGlvbiAoYWN0b3IpIHtcbiAgICAgICAgaWYgKGFjdG9yLm9uQ29tcGxldGUpIHtcbiAgICAgICAgICAgIGFjdG9yLm9uQ29tcGxldGUoYWN0b3IpO1xuICAgICAgICB9XG4gICAgfVxufSk7Il19