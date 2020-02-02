
/**
 *  图片添加水印方法
 *
 *	使用方法
 *	1. 创建 Watermask 实例
 *	2. 通过 setStatusListener() 方法监听实例的当前状态：当状态为 1 时，可以添加需要绘制水印的素材图片路径；当状态为 3 时，可以获取已经绘制完成的图片 src (base64 格式)，同时实例状态变为 1
 *	3. 通过 setSourceImage() 方法添加素材图片，方法接受一个图片路径数组
 *	4. 通过 getWatermaskImage() 方法可以获取绘制完水印的图片对象数组，在绘制过程中如果发生错误，则该项为错误原因，而不是图片对象（程序不会抛出错误）
 *
 *	参数说明
 *	@param opts {Object} required 配置项参数对象，具体配置见下方配置项参数说明
 *
 *	配置项参数说明
 *	@param maskImage {String} required 水印图片 src
 *	@param position {Object} optional 水印添加位置 {horizontal: left | center<default> | right, vertical: top | middle<default> | bottom}, 默认水平垂直居中
 *	@param margin {Int} optional 在非水平垂直居中的情况下，水印与图片边缘的距离，默认 10px
 *	@param scale {Float} optional 水印与图片的宽的比值，范围(0, 1]，默认 0.1
 */
define(function(){
  function _instanceof(left, right) { if (right != null && typeof Symbol !== "undefined" && right[Symbol.hasInstance]) { return !!right[Symbol.hasInstance](left); } else { return left instanceof right; } }

  function _classCallCheck(instance, Constructor) { if (!_instanceof(instance, Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

  function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

  function _createClass(Constructor, protoProps, staticProps) { if (protoProps) _defineProperties(Constructor.prototype, protoProps); if (staticProps) _defineProperties(Constructor, staticProps); return Constructor; }

  function Watermask(opts) {
    var _this = this;

    _classCallCheck(this, Watermask);

    // 目标图片缓存池 [{sourceImage, outputImage, status}]
    this.pool = []; // 当前工作状态
    // 0-prepare 准备中，水印图片未加载完成
    // 1-prepareFinish 准备完成，水印图片加载完成，但是还没有需要添加水印的图片
    // 2-pending 正在为图片添加水印
    // 3-complete 当前所有图片添加水印完成

    this.status = {
      code: 0,
      completeCounter: 0,
      listener: function listener(status) {
        return console.log(status);
      }
    };

    this._watchStatusChange(); // 水印边距


    this.margin = opts.margin || opts.margin == 0 ? opts.margin : 10; // 水印位置

    this.position = opts.position ? opts.position : {}; // 校验 postion 的值，如果不正确，则使用默认值

    this.position.horizontal = this.position.horizontal ? ['left', 'center', 'right'].indexOf(this.position.horizontal) > -1 ? this.position.horizontal : 'center' : 'center';
    this.position.vertical = this.position.vertical ? ['top', 'middle', 'bottom'].indexOf(this.position.vertical) > -1 ? this.position.vertical : 'middle' : 'middle'; // 水印与图片的比例
    // 校验比值是否在范围内，不在范围内或未设置则使用默认值

    this.scale = opts.scale && opts.scale > 0 && opts.scale <= 1 ? opts.scale : 0.1; // 水印图片
    this.quality = opts.quality ? opts.quality : 1;  //压缩图片质量

    this.radius = opts.radius ? opts.radius : 10;  //高斯模糊半径
    this.sigma = opts.sigma ? opts.sigma : 2.5;  //高斯模糊系数

    if (typeof opts.maskImage != 'string') {
      throw new Error('property maskImage must be a src string');
    } else {
      // maskImage 为字符串时，加载该图片
      var tempImage = new Image();

      tempImage.onload = function () {
        _this.maskImage = tempImage; // 水印图片加载完成，准备完毕

        _this.status.code = 1;
        tempImage = null;
      };

      tempImage.onerror = function () {
        tempImage = null;
        throw new Error('mask image load error');
      };

      tempImage.src = opts.maskImage;
    }
  }
  /**
   *  设置需要添加水印的图片，重复调用此方法，新的图片将会在原有图片完成之后开始
   *	由于添加水印的过程为异步，请在 status = 1 时调用
   *  @param sourceImageArray {Array<String>} required 需要添加水印的图片 src 列表
   */


  _createClass(Watermask, [{
    key: "setSourceImage",
    value: function setSourceImage(sourceImageArray) {
      var _this2 = this;

      if (this.status.code != 1) {
        console.warn('now is waiting for complete, cannot add more images');
        return;
      }

      if (!_instanceof(sourceImageArray, Array)) throw new Error('sourceImageArray must be an Array'); // 将目标图片存入缓存池
      // 如果不是字符串将会在输出图片时输出错误

      var errorCounter = 0; // 记录出错的项目数量

      sourceImageArray.forEach(function (item) {
        var errorMsg = '';

        if (typeof item != 'string') {
          errorMsg = 'source image must be a string: ' + item;
          errorCounter += 1;
        }

        _this2.pool.push({
          sourceImage: item,
          outputImage: null,
          error: errorMsg
        });
      }); // 遍历结束后，给完成计数赋值

      this._increaseCompleteCounter(errorCounter); // 水印图片未准备好，则等准备好后再执行


      this._loopImagePool();
    }
    /**
     *	获取添加完水印的图片
     *	由于添加水印的过程为异步，请在 status 变为 3 时调用
     *	@return outputImageArray {Array<Image>} 添加完水印的图片列表，顺序与输入的图片顺序相同
     */

  }, {
    key: "getWatermaskImage",
    value: function getWatermaskImage() {
      if (this.status.code != 3) return;
      var outputImageArray = this.pool.map(function (item) {
        if (!item.error) return item.outputImage;else return new Error(item.error);
      });
      this.pool = []; // 取出图片后清空缓存池

      this.status.completeCounter = 0; // 清空已完成计数

      return outputImageArray;
    }
    /**
     *  监听当前运行状态，当发生改变时调用用户自定义方法
     *	@param listener {Function} 监听器方法，有参数 status 表示当前状态
     */
    // 0-prepare 准备中，水印图片未加载完成
    // 1-prepareFinish 准备完成，水印图片加载完成，但是还没有需要添加水印的图片
    // 2-pending 正在为图片添加水印
    // 3-complete 当前所有图片添加水印完成

  }, {
    key: "setStatusListener",
    value: function setStatusListener(listener) {
      this.status.listener = listener && typeof listener == 'function' ? listener : void 0; // 如果在设置监听器之前，状态已经改变，则手动执行一次监听器方法

      if (this.status.code == 1) this.status.listener(this.status.code);
    }
    /**************************************警告***************************************/

    /********************************* 以下为私有方法 **********************************/

    /**********************************请勿调用及修改***********************************/

    /**
     *	状态监测，状态改变时主动调用 listener
     */

  }, {
    key: "_watchStatusChange",
    value: function _watchStatusChange() {
      var self = this;
      Object.defineProperty(self.status, 'code', {
        enumerable: true,
        configurable: true,
        get: function get() {
          return this.codeValue || 0;
        },
        set: function set(newVal) {
          if (this.codeValue !== newVal) {
            this.codeValue = newVal;
            self.status.listener(newVal);
          }
        }
      });
      Object.defineProperty(self.status, 'completeCounter', {
        enumerable: true,
        configurable: true,
        get: function get() {
          return this.counterValue || 0;
        },
        set: function set(newVal) {
          if (this.counterValue !== newVal) {
            this.counterValue = newVal;

            self._checkComplete();
          }
        }
      });
    }
    /**
     *	遍历缓存池中的项目
     */

  }, {
    key: "_loopImagePool",
    value: function _loopImagePool() {
      this.status.code = 2;

      for (var i = 0; i < this.pool.length; i++) {
        var item = this.pool[i];

        this._beforeAddWatermask(i, item);
      }
    }
    /**
     *	向图片上添加水印之前的操作，加载图片，如果加载出错则标记错误
     *	@param i {Int} 当前缓存池中的项目索引
     *	@param item {Object} 当前缓存池中的项目
     */

  }, {
    key: "_beforeAddWatermask",
    value: function _beforeAddWatermask(i, item) {
      var _this3 = this;

      var tempImage = new Image(); // 图片加载完成，开始添加水印

      tempImage.onload = function () {
        _this3._addWatermask(i, tempImage);

        tempImage = null;
      }; // 图片加载出错，标记错误，并增加完成计数


      tempImage.onerror = function () {
        _this3.pool[i].error = 'load image error: ' + item.sourceImage;

        _this3._increaseCompleteCounter(1);

        tempImage = null;
      };

      tempImage.src = item.sourceImage;
    }
    /**
     *	添加水印
     *	@param i {Int} 当前缓存池中的项目索引
     *	@param image {Image} 当前要添加水印的图片对象
     */

  }, {
    key: "_addWatermask",
    value: function _addWatermask(i, image) {
      var _this4 = this;

      // 创建 canvas 用来绘制图片和水印
      var canvas = document.createElement('canvas');
      var ctx = canvas.getContext('2d'); // 创建一个绘制完水印的图片承载容器

      var completedImage = new Image();
      completedImage.crossOrigin = "Anonymous"; // 设置图片跨域
      // 绘制完的图片加载完后，修改缓存池中的该项目输出图像属性，并增加完成计数

      completedImage.onload = function () {
        _this4.pool[i].outputImage = completedImage;

        _this4._increaseCompleteCounter(1);

        completedImage = null;
        canvas = null;
        ctx = null;
      }; // 绘制完的图片加载失败，记录失败原因，并增加完成计数


      completedImage.onerror = function () {
        _this4.pool[i].error = 'load watermask image error: ' + _this4.pool[i].sourceImage;

        _this4._increaseCompleteCounter(1);

        completedImage = null;
        canvas = null;
        ctx = null;
      }; // 在 canvas 上绘制原图


      canvas.width = image.width;
      canvas.height = image.height;
      ctx.drawImage(image, 0, 0, image.width, image.height);
      var data = ctx.getImageData(0, 0, canvas.width, canvas.height);
      var emptyData = ctx.createImageData(225, 300);
      emptyData = this._gaussBlur(data);
      ctx.putImageData(emptyData, 0, 0); // 在 canvas 上绘制水印

      this._drawWatermaskOncanvasContext(ctx, this.maskImage, image); // 从canvas上将绘制完水印的图片转为 base64 并赋值给图片容器的 src

      var base64 = canvas.toDataURL("image/png");
      // var blob = _this4._b64toBlob(_this4._getData(base64),_this4._getContentType(base64));
      completedImage.src = base64;
    }
    /**
     * 绘制水印
     * @param ctx {Object} 当前需要绘制水印的 canvas 上下文
     * @param mask {Image} 水印图片对象
     * @param image {Image} 添加水印的图片对象
     */

  }, {
    key: "_drawWatermaskOncanvasContext",
    value: function _drawWatermaskOncanvasContext(ctx, mask, image) {
      // 计算绘制水印的尺寸
      var tempWidth = mask.width;
      mask.width = image.width * this.scale;
      mask.height = mask.width / tempWidth * mask.height; // 计算绘制水印的位置

      var positionX, positionY; // 水平方向

      switch (this.position.horizontal) {
        case 'left':
          positionX = this.margin;
          break;

        case 'right':
          positionX = image.width - mask.width - this.margin;
          break;

        case 'center':
          positionX = (image.width - mask.width) / 2;
      } // 垂直方向


      switch (this.position.vertical) {
        case 'top':
          positionY = this.margin;
          break;

        case 'bottom':
          positionY = image.height - mask.height - this.margin;
          break;

        case 'middle':
          positionY = (image.height - mask.height) / 2;
      }

      ctx.drawImage(mask, positionX, positionY, mask.width, mask.height);
    }
    /**
     * 增加完成计数
     * @param count {Int} 需要增加的计数
     */

  }, {
    key: "_increaseCompleteCounter",
    value: function _increaseCompleteCounter(count) {
      this.status.completeCounter = this.status.completeCounter + count;
    }
    /**
     * 校验当前缓存池中的图片是否已经全部添加水印
     * @param count {Int} 需要增加的计数
     */

  }, {
    key: "_checkComplete",
    value: function _checkComplete() {
      if (this.pool.length) {
        // 缓存池中存在项目，且已完成计数与缓存池项目数相同时，添加水印全部完成
        if (this.status.completeCounter == this.pool.length) this.status.code = 3;
      } else {
        // 当缓存池被清空，完成计数变为 0 时，更改 code = 1
        this.status.code = 1;
      }
    }
    /**
     * 给当前加载完成的图片添加高斯模糊
     * @param imgData 图片数据
     */

  }, {
    key: "_gaussBlur",
    value: function _gaussBlur(imgData) {
      var _this = this;
      var pixes = imgData.data;
      var width = imgData.width;
      var height = imgData.height;
      var gaussMatrix = [],
          gaussSum = 0,
          x,
          y,
          r,
          g,
          b,
          a,
          i,
          j,
          k,
          len;
      var radius = _this.radius;
      var sigma = _this.sigma;
      a = 1 / (Math.sqrt(2 * Math.PI) * sigma);
      b = -1 / (2 * sigma * sigma); //生成高斯矩阵

      for (i = 0, x = -radius; x <= radius; x++, i++) {
        g = a * Math.exp(b * x * x);
        gaussMatrix[i] = g;
        gaussSum += g;
      } //归一化, 保证高斯矩阵的值在[0,1]之间


      for (i = 0, len = gaussMatrix.length; i < len; i++) {
        gaussMatrix[i] /= gaussSum;
      } //x 方向一维高斯运算


      for (y = 0; y < height; y++) {
        for (x = 0; x < width; x++) {
          r = g = b = a = 0;
          gaussSum = 0;

          for (j = -radius; j <= radius; j++) {
            k = x + j;

            if (k >= 0 && k < width) {
              //确保 k 没超出 x 的范围
              //r,g,b,a 四个一组
              i = (y * width + k) * 4;
              r += pixes[i] * gaussMatrix[j + radius];
              g += pixes[i + 1] * gaussMatrix[j + radius];
              b += pixes[i + 2] * gaussMatrix[j + radius]; // a += pixes[i + 3] * gaussMatrix[j];

              gaussSum += gaussMatrix[j + radius];
            }
          }

          i = (y * width + x) * 4; // 除以 gaussSum 是为了消除处于边缘的像素, 高斯运算不足的问题
          // console.log(gaussSum)

          pixes[i] = r / gaussSum;
          pixes[i + 1] = g / gaussSum;
          pixes[i + 2] = b / gaussSum; // pixes[i + 3] = a ;
        }
      } //y 方向一维高斯运算


      for (x = 0; x < width; x++) {
        for (y = 0; y < height; y++) {
          r = g = b = a = 0;
          gaussSum = 0;

          for (j = -radius; j <= radius; j++) {
            k = y + j;

            if (k >= 0 && k < height) {
              //确保 k 没超出 y 的范围
              i = (k * width + x) * 4;
              r += pixes[i] * gaussMatrix[j + radius];
              g += pixes[i + 1] * gaussMatrix[j + radius];
              b += pixes[i + 2] * gaussMatrix[j + radius]; // a += pixes[i + 3] * gaussMatrix[j];

              gaussSum += gaussMatrix[j + radius];
            }
          }

          i = (y * width + x) * 4;
          pixes[i] = r / gaussSum;
          pixes[i + 1] = g / gaussSum;
          pixes[i + 2] = b / gaussSum;
        }
      }

      return imgData;
    }
  },{
    key: "_b64toBlob",
    value: function _b64toBlob(b64Data, contentType, sliceSize) {
      contentType = contentType || '';
      sliceSize = sliceSize || 512;

      var byteCharacters = atob(b64Data);
      var byteArrays = [];

      for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
        var slice = byteCharacters.slice(offset, offset + sliceSize);

        var byteNumbers = new Array(slice.length);
        for (var i = 0; i < slice.length; i++) {
          byteNumbers[i] = slice.charCodeAt(i);
        }

        var byteArray = new Uint8Array(byteNumbers);

        byteArrays.push(byteArray);
      }

      var blob = new Blob(byteArrays, { type: contentType });
      return blob;
    }
  },{
    key: "_getData",
        value: function _getData(base64) {
        return base64.substr(base64.indexOf("base64,") + 7, base64.length);
    }
  },{
    key: "_getContentType",
    value: function _getContentType(base64) {
      var type = base64.split(';')[0];
      var typeData = type.split(':')[1];
      return typeData;
    }
  }]);
  return {Watermask : Watermask};
});
