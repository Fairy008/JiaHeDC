spearate = {
	//分离田块区域
	spearateArea: function(initArea, modifyArea) {
		//仅限制于凸多边形，分离为两段不同区域，多段不同区域暂不考虑
		sameArea = [];
		iStartIndex = null;
		mStartIndex = null;
		iEndtIndex = null;
		mEndIndex = null;
		initDiffArea = [];
		modifyDiffArea = [];

		if (initArea != null && (modifyArea != null)) {
			if ((initArea.length > 3) && (modifyArea.length > 3)) {
				haveSamePoint = false

				for (var i in initArea) {
					for (var j in modifyArea) {
						if (spearate.compareFunc(initArea[i], modifyArea[j])) {
							iStartIndex = i;
							mStartIndex = j;
							var endpoints = spearate.getEndIndex(i, j, initArea, modifyArea)
							iEndtIndex = endpoints[0]
							mEndIndex = endpoints[1]
							console.log("iStartIndex:" + iStartIndex)
							console.log("mStartIndex:" + mStartIndex)
							console.log("iEndtIndex:" + iEndtIndex)
							console.log("mEndIndex:" + mEndIndex)
							return
						}
					}
				}
				if (!haveSamePoint) {
					//无交汇点，一个区域包裹另外一个区域
				}
			}
		}
		return [
			[],
			[]
		];
	},
	getEndIndex: function(indexi, indexj, initarea, modifyarea) {
		var initArea = initarea;
		var modifyArea = modifyarea;
		var n = indexi;
		var m = indexj;
		var endN = null;
		var endM = null;
		// console.log(initArea[n])
		// console.log(modifyArea[m])
		console.log(spearate.compareFunc(initArea[n], modifyArea[m]))
		while (spearate.compareFunc(initArea[n], modifyArea[m])) {
			n++
			m++
			if (n == (initArea.length - 1)) {
				n = 0;
			}
			if (m == (modifyArea.length - 1)) {
				m = 0;
			}
		}

		var beleftN = initArea.length - n;
		var beleftM = modifyArea.length - m;
		if (beleftN > beleftM) {
			for (var i = n; i < beleftN; i++) {
				if (spearate.compareFunc(initArea[i], modifyArea[j])) {
					getEndPoints(i, j, initArea, modifyArea);
				}
			}
		} else {
			for (var j = m; j < beleftM; j++) {
				if (spearate.compareFunc(initArea[i], modifyArea[j])) {
					getEndPoints(i, j, initArea, modifyArea);
				}
			}
		}

		return [n, m];
	},
	//
	getEndPoints: function(startA, startB, initArea, modifyArea) {
		var endA = startA;
		var endB = startB;
		while (spearate.compareFunc(initArea[startA], modifyArea[startB])) {
			endA++
			endB++
			if (endA == (initArea.length - 1)) {
				endA = 0;
			}
			if (endB == (modifyArea.length - 1)) {
				endB = 0;
			}
		}
		return [endA, endB];
	},
	compareFunc: function(A, B) {
		// console.log(A)
		// console.log(B)
		if (A[0] == B[0] && (A[1] == B[1])) {
			return true
		} else {
			return false
		}
	},

}
