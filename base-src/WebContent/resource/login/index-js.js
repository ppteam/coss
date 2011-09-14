//
var tbException = new Class({
			options : {
				ele : "tbException"
			},
			initialize : function(options) {
				this.options = $extend(this.options, options);
				//
				$(this.options.ele).getElement("input[type=button]").addEvent('click', function() {
							this.hideException();
						}.bind(this));

				if ($(this.options.ele).getElement("span[class=error]") != null) {
					this.showException();
				}
			},
			showException : function() {
				var myfx = new Fx.Morph($(this.options.ele), {
							onComplete : function() {
								this.hideException.delay(4000, this);
							}.bind(this)
						});
				myfx.start({
							display : "block",
							opacity : [0, 1],
							top : [-100, 0]
						});
			},
			hideException : function() {
				if ($(this.options.ele).getStyle("display") != "none") {
					var myfx = new Fx.Morph($(this.options.ele), {
								onComplete : function() {
									$(this.options.ele).setStyle("display", "none");
								}.bind(this)
							});
					myfx.start({
								opacity : 0,
								top : -100
							});
				}
			}
		});

var tbFadeInOut = new Class({
			options : {
				ele : "fadeElement",
				styles : {
					opacity : 0.01,
					display : 'block'
				},
				begin : 0.01,
				end : 0.99,
				delay : 1000,
				breath : false
			},
			initialize : function(options) {
				//
				this.options = $extend(this.options, options);
				//
				this.timer = null;
				this.enter = false;
				this.showen = false;
				//
				this.effect = new Fx.Morph($(this.options.ele), {
							onComplete : function() {
								if (this.options.breath && this.showen && !this.enter) {
									this.hide();
								}
							}.bind(this)
						});

				// bind event
				$(this.options.ele).addEvents({
							'mouseenter' : function() {
								$clear(this.timer);
								this.show();
								this.enter = true;
							}.bind(this),
							'mouseleave' : function() {
								this.timer = this.hide.delay(this.options.delay, this);
								this.enter = false;
							}.bind(this)
						});

				// set style
				$(this.options.ele).setStyles(this.options.styles);

				if (this.options.breath) {
					this.show.periodical(3000, this);
				}
			},

			show : function() {
				if (!(this.options.breath && this.enter)) {
					this.effect.cancel();
					this.effect.start({
								opacity : [$(this.options.ele).getStyle('opacity'), this.options.end]
							});
					this.showen = true;
				}
			},

			hide : function() {
				this.effect.cancel();
				this.effect.start({
							opacity : [$(this.options.ele).getStyle('opacity'), this.options.begin]
						});
				this.showen = false;
			}
		});
// old---
// popup a new window, while IE, will be a ModalDialog.
function popupnew(winUrl, winName, winWidth, winHeight, winTop, winLeft) {
	if (!winName) {
		winName = 'tbpopwindow';
	}
	if (!winWidth) {
		winWidth = 100;
	}
	if (!winHeight) {
		winHeight = 100;
	}
	if (!winTop) {
		winTop = 100;
	}
	if (!winLeft) {
		winLeft = 100;
	}
	var winArgument = "height=" + winHeight + ",width=" + winWidth + ",top=" + winTop + ",left=" + winLeft
			+ ",toolbar=no,location=no,scrollbars=yes,resizable=yes,status=no";
	tbPopupWindow = window.open(winUrl, winName, winArgument);
}
//
function selectCompany() {
	popupnew('<%= request.getContextPath() %>/login.do?state=selectCompany', 'selectCompany', 400, 300);
}
//
function selectDomain(mailSuffixValue) {
	document.LoginForm.mailSuffix.value = mailSuffixValue;
}
//
window.addEvent("load", function() {
			if ($("logon") != null) {
				$("logon").setStyles({
							left : ((window.getSize().x - 800) / 2 + 50) < 0 ? -50 : (window.getSize().x - 800) / 2,
							top : ((window.getSize().y - 600) / 2 + 30) < 0 ? -30 : (window.getSize().y - 600) / 2,
							display : "block",
							opacity : 0
						});

				document.getElement("div[class=logonSubmit]").addEvents({
							'click' : function() {
								this.getElement("input[type=button]").click();
							},

							'mouseover' : function() {
								$("logon").addClass("over");
							},

							'mouseout' : function() {
								$("logon").removeClass("over");
							}
						});

				var showLogon = new Fx.Morph($("logon"), {
							onComplete : function() {
								if ($("tbException") != null) {
									new tbException();
								}
								$("logon").getElement("input[type=text]").focus();
								//
								new tbFadeInOut({
											ele : "logonSubmit",
											delay : 300,
											breath : true
										});
							}
						});
				showLogon.start({
							opacity : 1
						});
			} else {
				var controls = $$("input[type!=hidden]");
				for (var i = 0; i < controls.length; i++) {
					if (controls[i].type == 'text') {
						controls[i].focus();
						if (controls[i].value != "") {
							controls[i].select();
						}
						break;
					}
				}
			}

			new tbFadeInOut({
						ele : "browserCompatibility",
						styles : {
							width : 300,
							height : 50,
							display : 'block',
							opacity : 0.15
						},
						begin : 0.15,
						end : 0.85
					});
		});
//
window.addEvent("resize", function() {
			if ($("logon") != null) {
				$("logon").setStyles({
							left : ((window.getSize().x - 800) / 2 + 50) < 0 ? -50 : (window.getSize().x - 800) / 2,
							top : ((window.getSize().y - 600) / 2 + 30) < 0 ? -30 : (window.getSize().y - 600) / 2
						});
			}
		});
