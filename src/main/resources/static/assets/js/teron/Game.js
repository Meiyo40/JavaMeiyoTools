class TeronGame {

    constructor() {
        this.interval = null;
        this.ctx = null;
        this.canvas = null;
        this.frameRate = 60;
        this.hasStarted = false;
        this.init();
        setTimeout( () => {
            this.hasStarted = true;
        }, 3000);
    }

    init() {
        this.interval = setInterval(() => {
            this.elapsed = Date.now() - this.elapsed;
            this.dt = this.elapsed / this.frameRate;
            this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
            if(this.hasStarted != true) {
                this.startMessage();
            } else {
                this.update();
                this.draw(this.ctx);
            }
        }, 1000 / this.frameRate);

        this.canvas = document.getElementById("game-canvas");
        this.ctx = this.canvas.getContext("2d");

        this.player = new Player("player");
        this.player.init(this.canvas);

        this.player.x = 30;
        this.player.y = 30;
        this.elapsed = Date.now();

        this.ghosts = new Array();

        this.teron = new Teron(this.canvas.width / 2, 22);
        this.ui = { spells: new Array() };

        this.ui.spells = this.initSpellsUi();

        window.addEventListener("keydown", (e) => {
            if (e.key == "Enter") {
                location.reload();
            }
            if (e.code === "Digit1") {
                this.player.shot("strike");
            } else if (e.code === "Digit2") {
                this.player.shot("lance");
            } else if (e.code === "Digit3") {
                this.player.shot("chains", this.ghosts);
            } else if (e.code === "Digit4") {
                this.player.shot("volley");
            } else if (e.code === "Digit5") {
                this.player.shot("shield");
            } else if (e.key == "r") {
                this.player.selectTarget(this.ghosts);
            }
            this.player.input(e)
        }, false);
        window.addEventListener("keyup", (e) => { this.player.output(e) }, false);
        this.canvas.addEventListener("click", (e) => {
            let mx = e.pageX - (this.canvas.offsetLeft + this.canvas.clientLeft);
            let my = e.pageY - (this.canvas.offsetTop + this.canvas.clientTop);
            this.collider(mx, my)
        });


        console.log("init");
    }

    update() {
        this.player.update();
        if (this.player.debuff.remaining <= 0 && this.player.debuff.spawned == false) {
            this.spawnGhost();
        }

        if (this.ghosts.length > 0) {
            this.ghosts.forEach((g) => {
                if (g.status.alive) {
                    g.friends = this.ghosts;
                    g.update(this.dt);
                    if (g.collide(this.teron.x, this.teron.y)) {
                        g.status.alive = false;
                        this.teron.ghostHit++;
                    }
                }
            });
        }

        if (this.teron.ghostHit > 1) {
            clearInterval(this.interval);
            this.gameOver();
        } else if (this.ghosts.length <= 0 && this.player.spawned) {
            this.win();
        }
    }

    draw(ctx) {
        //teron
        ctx.drawImage(this.teron.image, this.teron.x - (this.teron.image.width / 2), this.teron.y - (this.teron.image.height / 2), this.teron.image.width, this.teron.image.height);

        //player
        if (this.player.debuff.remaining > 0) {
            ctx.drawImage(this.player.debuff.image, this.player.debuff.x, this.player.debuff.y, this.player.debuff.image.width, this.player.debuff.image.height);
            ctx.font = "20px Arial";
            ctx.fillStyle = "white";
            ctx.textAlign = "center";
            let text = this.player.debuff.remaining + "s";
            ctx.fillText(text, this.player.debuff.x + 15, this.player.debuff.y + 48);
        }
        ctx.drawImage(this.player.image, this.player.x - (this.player.image.width / 2), this.player.y - (this.player.image.height / 2), this.player.image.width, this.player.image.height);

        //ghost
        if (this.ghosts.length > 0) {
            this.ghosts.forEach((e) => {
                if (e.status.alive) {

                    ctx.beginPath();
                    ctx.rect(e.x - 15, e.y - 20, 30, 0);
                    ctx.lineWidth = 7;
                    ctx.strokeStyle = 'red';
                    ctx.stroke();

                    ctx.beginPath();
                    ctx.lineWidth = 7;

                    if (e.status.debuff.stun.active) {
                        ctx.fillStyle = 'cyan';
                    } else if (e.status.debuff.snare.active) {
                        ctx.fillStyle = 'blue';
                    } else {
                        ctx.fillStyle = 'green';
                    }

                    ctx.rect(e.x - 16, e.y - 24, 30 * e.getPercentHealth() + 2, 8);
                    ctx.fill();

                    ctx.drawImage(e.image, e.x - (e.image.width / 2), e.y - (e.image.height / 2), e.image.width, e.image.height);
                }
            })
        }

        //spells
        if (this.player.shots.length > 0) {
            this.player.shots.forEach((e) => {
                e.draw(ctx);
            })
        }

        //ui
        this.ui.spells.forEach((btn) => {
            ctx.drawImage(btn.image, btn.x, btn.y, btn.image.width, btn.image.height);
            if (btn.data.current_cooldown > 0) {
                ctx.font = "bold 20px Arial";
                ctx.shadowColor = "white";
                ctx.shadowBlur = 7;
                ctx.lineWidth = 5;
                ctx.strokeText(btn.data.current_cooldown, btn.x + 15, btn.y + 25);
                ctx.shadowBlur = 0;
                ctx.fillStyle = "black";
                ctx.fillText(btn.data.current_cooldown, btn.x + 15, btn.y + 25);
            }
        })
    }

    spawnGhost() {
        this.player.debuff.spawned = true;

        let xCorrected = this.setGhostX(this.player.x - 30);
        let yCorrected = this.setGhostY(this.player.y - 30);
        this.ghosts.push(new Ghost(1, { x: xCorrected, y: yCorrected}, this.canvas));

        xCorrected = this.setGhostX(this.player.x + 30);
        yCorrected = this.setGhostY(this.player.y - 30);
        this.ghosts.push(new Ghost(2, { x: xCorrected, y: yCorrected }, this.canvas));

        xCorrected = this.setGhostX(this.player.x - 30);
        yCorrected = this.setGhostY(this.player.y + 30);
        this.ghosts.push(new Ghost(3, { x: xCorrected, y: yCorrected }, this.canvas));

        xCorrected = this.setGhostX(this.player.x + 30);
        yCorrected = this.setGhostY(this.player.y + 30);
        this.ghosts.push(new Ghost(4, { x: xCorrected, y: yCorrected }, this.canvas));
    }

    setGhostX(x) {
        let xCorrected = x;
        if(xCorrected < 5) {
            xCorrected = 15;
        }
        if(xCorrected >= (this.canvas.width - 5)) {
            xCorrected = this.canvas.width - 15;
        }
        return xCorrected;
    }

    setGhostY(y) {
        let yCorrected = y;
        if(yCorrected < 5) {
            yCorrected = 15;
        }
        if(yCorrected >= (this.canvas.height - 5)) {
            yCorrected = this.canvas.height - 15;
        }
        return yCorrected;
    }

    startMessage() {
        this.ctx.font = "20px Arial";
        this.ctx.fillStyle = "white";
        this.ctx.textAlign = "center";
        this.ctx.fillText("'R' replace tab ingame key.", this.canvas.width / 2, this.canvas.height / 2);
        this.ctx.fillText("Have Fun!", this.canvas.width / 2, this.canvas.height / 2 + 42);
    }

    gameOver() {
        this.ctx.font = "40px Arial";
        this.ctx.fillStyle = "white";
        this.ctx.textAlign = "center";
        this.ctx.fillText("Ouk ouk!", this.canvas.width / 2, this.canvas.height / 2);
        this.ctx.fillText("Raid is Dead!", this.canvas.width / 2, this.canvas.height / 2 + 42);
    }

    win() {
        this.ctx.font = "40px Arial";
        this.ctx.fillStyle = "white";
        this.ctx.textAlign = "center";
        this.ctx.fillText("HAOU HAOU!", this.canvas.width / 2, this.canvas.height / 2);
        this.ctx.fillText("Banane pour toi!", this.canvas.width / 2, this.canvas.height / 2 + 42);
    }

    collider(mX, mY) {
        //ghost
        if (this.ghosts.length > 0) {
            let hasTouch = { state: false, target: null };
            this.ghosts.forEach((e) => {
                let x = e.x - e.image.width / 2;
                if ((mX < x + e.image.width) &&
                    (mX > x) &&
                    (mY < e.y + Math.floor(e.image.height/2)) &&
                    (mY > e.y)) {
                    if (hasTouch.state && hasTouch.target != null) {
                        hasTouch.target.select(false);
                        hasTouch.target = null;
                    } else {
                        this.player.target = e;
                        hasTouch.state = true;
                        hasTouch.target = e;
                        e.select(true);
                    }
                } else {
                    e.select(false);
                }
            });
            this.player.target = hasTouch.target;
        }
    }

    initSpellsUi() {

        let spells = new Array();
        let BaseX = 70;
        this.player.spells.forEach((spell) => {
            let img = new Image();
            img.src = spell.image;
            img.width = 30;
            img.height = 30;
            spells.push({ x: BaseX, y: this.canvas.height - 30, image: img, data: spell });
            BaseX = BaseX + img.width + 5;
            if (spell.current_cooldown > 0) {
                setInterval(() => {
                    spell.current_cooldown--;
                }, 1000)
            }
        })

        return spells;
    }
}

let game = new TeronGame();