var TabEventLoop = {
	initEventLoop: function() {
		// console.log('12334');
		var tabEventLoop = {};
		tabEventLoop.loopArray = [];
		tabEventLoop.timeIV = 300;
		tabEventLoop.setEvent = function(tabEvent) {
			//队列中仅保留1个待执行函数
			if (tabEventLoop.loopArray.length > 1) {
				tabEventLoop.loopArray.splice(0, 1)
			}
			var func = function() {
				tabEvent();
			}
			tabEventLoop.loopArray.push(func);
		};
		tabEventLoop.setTimeIV = function(timeMs) {
			tabEventLoop.timeIV = timeMs;
		};
		tabEventLoop.myLoop = function() {
			myInterval = setInterval(function() {
				setTimeout(function() {
					if (tabEventLoop.loopArray.length > 0) {
						// console.log(tabEventLoop.loopArray[0])
						tabEventLoop.loopArray[0]();
						tabEventLoop.loopArray.splice(0, 1)
					}
				}, 0)
			}, tabEventLoop.timeIV)
		};

		tabEventLoop.closeloop = function() {
			clearInterval(myInterval);
		};
		return tabEventLoop;
	}
};
