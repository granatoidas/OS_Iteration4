
public class EntryPoint {
	static CPU cpu = new CPU();
	static GUI gui;

	// sdfsdfsdfsdfs
	public static void main(String[] args) {
		Process startStop = new Process(0, 0, "StarStop") {
			void run() {
				switch (this.segmentToRunFrom) {
				case 0:
					cpu.kurtiProcesa(new Process(Utils.getId(), this.id, "ReadFromHDD") {
						void run() {
							while (true) {
								switch (this.segmentToRunFrom) {
								case 0:
									cpu.prasytiResurso("Pageidautina programa");
									this.segmentToRunFrom = 1;
									return;
								case 1:
									cpu.prasytiResurso("Kietasis diskas");
									this.segmentToRunFrom = 2;
									return;
								case 2:
									cpu.prasytiResurso("Supervizoriaus atmintis");
									this.segmentToRunFrom = 3;
									return;
								case 3:
									Resource r = cpu.kurtiResursa("Uzduotis supervizorineje atmintyje",
											resourcesInPossesion.get(0).data);
									cpu.atlaisvintiResursa(r);
									cpu.naikintiResursa(resourcesInPossesion.get(0));
									cpu.atlaisvintiResursa(resourcesInPossesion.get(0));
									cpu.atlaisvintiResursa(resourcesInPossesion.get(0));
								}
								this.segmentToRunFrom = 0;
							}
						}
					});
					cpu.kurtiProcesa(new Process(Utils.getId(), this.id, "JCL") {
						void run() {
							while (true) {
								switch (this.segmentToRunFrom) {
								case 0:
									cpu.prasytiResurso("Uzduotis supervizorineje atmintyje");
									this.segmentToRunFrom = 1;
									return;
								case 1:
									Resource r = this.resourcesInPossesion.get(this.resourcesInPossesion.size() - 1);
									cpu.naikintiResursa(r);
									if (r.data.equals("SpausdinkLabas") || r.data.equals("Isjungti")
											|| r.data.equals("SpausdinkIvesti")) {
										Resource nr = cpu.kurtiResursa("MainProc Uzduotis", r.data);
										cpu.atlaisvintiResursa(nr);
									} else {
										Resource nr = cpu.kurtiResursa("PrintRequest", "nera vartotojo programos");
										cpu.atlaisvintiResursa(nr);
									}
								}
								this.segmentToRunFrom = 0;
							}
						}
					});
					cpu.kurtiProcesa(new Process(Utils.getId(), this.id, "Loader") {
						void run() {
							while (true) {
								switch (this.segmentToRunFrom) {
								case 0:
									cpu.prasytiResurso("LoaderJob");
									this.segmentToRunFrom = 1;
									return;
								case 1:
									Resource loaderJob = resourcesInPossesion.get(0);
									cpu.naikintiResursa(loaderJob);
									cpu.atlaisvintiResursa(cpu.kurtiResursa("LoaderDone", loaderJob.parentId, null));
								}
								this.segmentToRunFrom = 0;
							}
						}
					});
					cpu.kurtiProcesa(new Process(Utils.getId(), this.id, "MainProc") {
						void run() {
							while (true) {
								switch (this.segmentToRunFrom) {
								case 0:
									cpu.prasytiResurso("MainProc Uzduotis");
									this.segmentToRunFrom = 1;
									return;
								case 1:
									Resource r = this.resourcesInPossesion.get(this.resourcesInPossesion.size() - 1);
									if (r.data.equals("deleteJobGovernor")) {
										cpu.naikintiProcesa(r.parent);
									} else {
										cpu.kurtiProcesa(new JobGovernor(Utils.getId(), this.id, r.data));
									}
									cpu.naikintiResursa(r);
								}
								this.segmentToRunFrom = 0;
							}
						}
					});
					cpu.kurtiProcesa(new Process(Utils.getId(), this.id, "Interrupt") {
						void run() {
							while (true) {
								switch (this.segmentToRunFrom) {
								case 0:
									cpu.prasytiResurso("Pertraukimas");
									this.segmentToRunFrom = 1;
									return;
								case 1:
									Resource r = resourcesInPossesion.get(0);
									cpu.atlaisvintiResursa(cpu.kurtiResursa("UserProcStatus", r.parent.parentId, r.data));
									cpu.naikintiResursa(r);
								case 2:
									;
								}
								this.segmentToRunFrom = 0;
							}
						}
					});
					cpu.kurtiProcesa(new Process(Utils.getId(), this.id, "PrintLine") {
						void run() {
							while (true) {
								switch (this.segmentToRunFrom) {
								case 0:
									cpu.prasytiResurso("PrintRequest");
									this.segmentToRunFrom = 1;
									return;
								case 1:
									cpu.prasytiResurso("Kanalu irenginys");
									this.segmentToRunFrom = 2;
									return;
								case 2:
									gui.print(resourcesInPossesion.get(0).data);
									cpu.atlaisvintiResursa(resourcesInPossesion.get(1));
									cpu.naikintiResursa(resourcesInPossesion.get(0));
								}
								this.segmentToRunFrom = 0;
							}
						}
					});
					cpu.kurtiProcesa(new Process(Utils.getId(), this.id, "ReadFromUser") {
						void run() {
							while (true) {
								switch (this.segmentToRunFrom) {
								case 0:
									cpu.prasytiResurso("Kanalu irenginys");
									this.segmentToRunFrom = 1;
									return;
								case 1:
									String s = gui.getInput();

									if (s.length() > 1 && s.substring(0, 2).equals("./")) {
										cpu.atlaisvintiResursa(
												cpu.kurtiResursa("Pageidautina programa", s.substring(2)));
									} else if (!s.equals("")) {
										cpu.atlaisvintiResursa(cpu.kurtiResursa("Vartotojo ivestis", s));
									}

									cpu.atlaisvintiResursa(resourcesInPossesion.get(0));
								}
								this.segmentToRunFrom = 0;
							}
						}
					});

					cpu.atlaisvintiResursa(cpu.kurtiResursa("Kanalu irenginys", null));
					cpu.atlaisvintiResursa(cpu.kurtiResursa("Supervizoriaus atmintis", null));
					for (int i = 0; i < 15; i++)
						cpu.atlaisvintiResursa(cpu.kurtiResursa("Vartotojo atminties blokas", null));
					cpu.atlaisvintiResursa(cpu.kurtiResursa("Kietasis diskas", null));
					this.segmentToRunFrom = 1;
					cpu.prasytiResurso("MOS pabaiga");
					return;
				case 1:
					System.exit(0);
				}
			}
		};
		gui = new GUI();
		Utils.LOG("Sukurtas procesas StartStop");
		cpu.pasiruose_procesai.add(startStop);
		cpu.planutojas();
	}

}

class JobGovernor extends Process {
	String data;
	CPU cpu = EntryPoint.cpu;

	JobGovernor(int id, int parentId, String data) {
		super(id, parentId, "JobGovernor");
		this.data = data;
	}

	void run() {
		switch (this.segmentToRunFrom) {
		case 0:
			cpu.prasytiResurso("Vartotojo atminties blokas");
			this.segmentToRunFrom = 1;
			return;
		case 1:
			cpu.atlaisvintiResursa(cpu.kurtiResursa("LoaderJob", null));
			cpu.prasytiResurso("LoaderDone");
			this.segmentToRunFrom = 2;
			return;
		case 2:
			cpu.naikintiResursa(this.resourcesInPossesion.get(1));
			cpu.kurtiProcesa(new VirtualMachine(Utils.getId(), this.id, this.data));
			cpu.prasytiResurso("UserProcStatus");
			this.segmentToRunFrom = 3;
			return;
		case 3:
			Resource r = this.resourcesInPossesion.get(1);
			cpu.naikintiResursa(r);
			if (r.data.equals("IN")) {
				cpu.prasytiResurso("Vartotojo ivestis");
				this.segmentToRunFrom = 4;
				return;
			} else if (r.data.equals("OUT")) {
				cpu.atlaisvintiResursa(cpu.kurtiResursa("PrintRequest", ((VirtualMachine) children.get(0)).dataField));
				cpu.aktyvuotiProcesa(children.get(0));
				cpu.prasytiResurso("UserProcStatus");
				this.segmentToRunFrom = 3;
				return;
			} else {
				break;
			}
		case 4:
			Resource rn = resourcesInPossesion.get(1);
			((VirtualMachine) children.get(0)).dataField = rn.data;
			cpu.naikintiResursa(rn);
			cpu.aktyvuotiProcesa(children.get(0));
			cpu.prasytiResurso("UserProcStatus");
			this.segmentToRunFrom = 3;
			return;
		}
		cpu.naikintiProcesa(children.get(0));
		cpu.atlaisvintiResursa(resourcesInPossesion.get(0));
		cpu.atlaisvintiResursa(cpu.kurtiResursa("MainProc Uzduotis", "deleteJobGovernor"));
		cpu.stabdytiProcesa(this);
	}

}

class VirtualMachine extends Process {
	String data;
	CPU cpu = EntryPoint.cpu;
	String dataField;

	VirtualMachine(int id, int parentId, String data) {
		super(id, parentId, "VirtualMachine");
		this.data = data;
	}

	void run() {
		switch (this.data) {
		case "SpausdinkLabas":
			SpausdinkLabas();
			break;
		case "Isjungti":
			Isjungti();
			break;
		case "SpausdinkIvesti":
			SpausdinkIvesti();
			break;
		}
		cpu.stabdytiProcesa(this);
	}

	void SpausdinkLabas() {
		switch (this.segmentToRunFrom) {
		case 0:
			dataField = "labas";
			cpu.atlaisvintiResursa(cpu.kurtiResursa("Pertraukimas", "OUT"));
			this.segmentToRunFrom = 1;
			return;
		case 1:
			cpu.atlaisvintiResursa(cpu.kurtiResursa("Pertraukimas", ""));
			return;
		}
	}

	void Isjungti() {
		switch (this.segmentToRunFrom) {
		case 0:
			cpu.atlaisvintiResursa(cpu.kurtiResursa("MOS pabaiga", ""));
			return;
		}
	}

	void SpausdinkIvesti() {
		switch (this.segmentToRunFrom) {
		case 0:
			cpu.atlaisvintiResursa(cpu.kurtiResursa("Pertraukimas", "IN"));
			this.segmentToRunFrom = 1;
			return;
		case 1:
			cpu.atlaisvintiResursa(cpu.kurtiResursa("Pertraukimas", "OUT"));
			this.segmentToRunFrom = 2;
			return;
		case 2:
			cpu.atlaisvintiResursa(cpu.kurtiResursa("Pertraukimas", ""));
			return;
		}
	}

}
