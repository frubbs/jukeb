'use strict';

module.exports = function (values, terms, delimiter, chop) {
    var combined = '',
        key = '',
        i = 0,
        numTerms = terms.length;

    for (; i < numTerms; i++) {
        key = terms[i];

        if (values.hasOwnProperty(key)) {
            combined += values[key] + delimiter;
        }
    }

    if (chop) {
        combined = combined.slice(0, -chop);
    }

    return combined;
};
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi4uLy4uLy4uL3NyYy92YWx1ZS10eXBlcy9tYW5pcHVsYXRvcnMvY3JlYXRlLWRlbGltaXRlZC5qcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOztBQUFBLE1BQU0sQ0FBQyxPQUFPLEdBQUcsVUFBQyxNQUFNLEVBQUUsS0FBSyxFQUFFLFNBQVMsRUFBRSxJQUFJLEVBQUs7QUFDakQsUUFBSSxRQUFRLEdBQUcsRUFBRTtRQUNiLEdBQUcsR0FBRyxFQUFFO1FBQ1IsQ0FBQyxHQUFHLENBQUM7UUFDTCxRQUFRLEdBQUcsS0FBSyxDQUFDLE1BQU0sQ0FBQzs7QUFFNUIsV0FBTyxDQUFDLEdBQUcsUUFBUSxFQUFFLENBQUMsRUFBRSxFQUFFO0FBQ3RCLFdBQUcsR0FBRyxLQUFLLENBQUMsQ0FBQyxDQUFDLENBQUM7O0FBRWYsWUFBSSxNQUFNLENBQUMsY0FBYyxDQUFDLEdBQUcsQ0FBQyxFQUFFO0FBQzVCLG9CQUFRLElBQUksTUFBTSxDQUFDLEdBQUcsQ0FBQyxHQUFHLFNBQVMsQ0FBQztTQUN2QztLQUNKOztBQUVELFFBQUksSUFBSSxFQUFFO0FBQ04sZ0JBQVEsR0FBRyxRQUFRLENBQUMsS0FBSyxDQUFDLENBQUMsRUFBRSxDQUFDLElBQUksQ0FBQyxDQUFDO0tBQ3ZDOztBQUVELFdBQU8sUUFBUSxDQUFDO0NBQ25CLENBQUMiLCJmaWxlIjoiY3JlYXRlLWRlbGltaXRlZC5qcyIsInNvdXJjZXNDb250ZW50IjpbIm1vZHVsZS5leHBvcnRzID0gKHZhbHVlcywgdGVybXMsIGRlbGltaXRlciwgY2hvcCkgPT4ge1xuICAgIHZhciBjb21iaW5lZCA9ICcnLFxuICAgICAgICBrZXkgPSAnJyxcbiAgICAgICAgaSA9IDAsXG4gICAgICAgIG51bVRlcm1zID0gdGVybXMubGVuZ3RoO1xuXG4gICAgZm9yICg7IGkgPCBudW1UZXJtczsgaSsrKSB7XG4gICAgICAgIGtleSA9IHRlcm1zW2ldO1xuXG4gICAgICAgIGlmICh2YWx1ZXMuaGFzT3duUHJvcGVydHkoa2V5KSkge1xuICAgICAgICAgICAgY29tYmluZWQgKz0gdmFsdWVzW2tleV0gKyBkZWxpbWl0ZXI7XG4gICAgICAgIH1cbiAgICB9XG5cbiAgICBpZiAoY2hvcCkge1xuICAgICAgICBjb21iaW5lZCA9IGNvbWJpbmVkLnNsaWNlKDAsIC1jaG9wKTtcbiAgICB9XG5cbiAgICByZXR1cm4gY29tYmluZWQ7XG59OyJdfQ==