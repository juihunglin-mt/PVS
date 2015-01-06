$(function(){

	// ==================== MENU ====================== //

	var $menu = $('.menu'),
		$menuItem = $menu.find('li'),
		_menuItemH = $menuItem.height(),
		_menuItemNum = $menuItem.length,
		$subMenu = $('.submenu'),
		$subMenuItem = $('.submenu>ul'),
		$closeBtn = $subMenu.find('.close-btn'),
		$content = $('.content'),
		$arrowUp = $('.menu-arrow-up'),
		$arrowDown = $('.menu-arrow-down'),
		_headerH = $('.header').height();

	$arrowUp.hide();
	arrowVisible();

	$menuItem.click(function(event) {
		var $this = $(this),
			_index = $this.index();

		if (!$this.hasClass('menu-0')) {
			$this.addClass('selected').siblings().removeClass('selected');
			$subMenu.addClass('open', 500);
			$subMenuItem.eq(_index).show().siblings().hide();
			// $content.addClass('open', 500);
		};
	});

	$content.add($closeBtn).click(function(event) {
		$menuItem.removeClass('selected');
		$subMenu.removeClass('open', 500);
		// $content.removeClass('open', 500);
	});

	$(window).resize(function() {
		// console.log(_headerH, _menuItemH * _menuItemNum + _headerH,document.documentElement.clientHeight);
		arrowVisible();
	});

	function arrowVisible(){
		$menu.stop().animate({top: _headerH});
		$arrowUp.hide();
		if(document.documentElement.clientHeight < _menuItemH * _menuItemNum + _headerH){
			// $arrowUp.show();
			$arrowDown.show();
		}else{
			$arrowDown.hide();
		}
	}

	$arrowUp.click(function(event) {
		$menu.stop().animate({top: _headerH});
		$menu.css({top: _headerH});
		$arrowUp.hide();
		$arrowDown.show();
	});

	$arrowDown.click(function(event) {
		$arrowUp.show();
		$arrowDown.hide();
		// console.log($menu.offset().top, _menuItemH * _menuItemNum, document.documentElement.clientHeight, _headerH);

		$menu.stop().animate({top: $menu.offset().top -(_menuItemH * _menuItemNum - (document.documentElement.clientHeight-_headerH))});
	});

	// =============================================== //

});