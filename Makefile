docker-run:
	docker-compose up -d --build
	python ./tools/api-requests/api-requests.py -w 15 -v

docker-stop:
	docker-compose down

install:
	pip install -r ./tools/api-requests/requirements.txt

run:
	powershell -File ./tools/local-run.ps1
	python ./tools/api-requests/api-requests.py -w 30 -v
